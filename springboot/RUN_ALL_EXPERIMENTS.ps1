param(
    [string]$OutputDirectory = "$PSScriptRoot\demo-output\latest"
)

$ErrorActionPreference = "Stop"
$ProjectRoot = Join-Path $PSScriptRoot "springboot"
New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
$ResultsFile = Join-Path $OutputDirectory "results.txt"
Set-Content -Path $ResultsFile -Value "Event-Driven Microservices - Demo Results`r`n"

function Write-Result([string]$Title, $Value) {
    $text = if ($Value -is [string]) { $Value } else { $Value | ConvertTo-Json -Depth 8 -Compress }
    Add-Content -Path $ResultsFile -Value "`r`n[$Title]`r`n$text"
    Write-Host "[$Title] $text"
}

function Get-Jar([string]$RelativeProject) {
    $target = Join-Path (Join-Path $ProjectRoot $RelativeProject) "target"
    $jar = Get-ChildItem $target -Filter "*.jar" |
        Where-Object { $_.Name -notlike "*.original" } |
        Select-Object -First 1
    if (-not $jar) { throw "No packaged jar found in $target. Run the package commands first." }
    return $jar.FullName
}

function Start-App([string]$Name, [string]$RelativeProject, [int]$Port) {
    $stdout = Join-Path $OutputDirectory "$Name.log"
    $stderr = Join-Path $OutputDirectory "$Name-error.log"
    $jar = Get-Jar $RelativeProject
    $process = Start-Process -FilePath "java" -ArgumentList @("-jar", $jar) `
        -WorkingDirectory (Split-Path $jar -Parent) -WindowStyle Hidden -PassThru `
        -RedirectStandardOutput $stdout -RedirectStandardError $stderr

    for ($attempt = 0; $attempt -lt 45; $attempt++) {
        if ($process.HasExited) { throw "$Name stopped during startup. Check $stderr" }
        $client = [System.Net.Sockets.TcpClient]::new()
        try {
            $client.Connect("127.0.0.1", $Port)
            $client.Dispose()
            return $process
        } catch {
            $client.Dispose()
            Start-Sleep -Milliseconds 500
        }
    }
    Stop-Process -Id $process.Id -Force -ErrorAction SilentlyContinue
    throw "$Name did not listen on port $Port"
}

function Stop-Apps($Processes) {
    foreach ($process in $Processes) {
        if ($process -and -not $process.HasExited) {
            Stop-Process -Id $process.Id -Force -ErrorAction SilentlyContinue
            $process.WaitForExit(5000) | Out-Null
        }
    }
    Start-Sleep -Milliseconds 700
}

function Reset-Rabbit([string[]]$Queues) {
    foreach ($queue in $Queues) {
        $oldPreference = $ErrorActionPreference
        $ErrorActionPreference = "Continue"
        docker exec assignment-rabbitmq rabbitmqctl delete_queue $queue 2>$null | Out-Null
        $ErrorActionPreference = $oldPreference
    }
}

function Post-Form([string]$Uri) {
    return Invoke-RestMethod -Method Post -Uri $Uri
}

Write-Host "Running Experiment 1"
$apps = @()
try {
    $apps += Start-App "exp1-service" "exp1\restaurant-service" 8080
    Write-Result "Experiment 1" (Invoke-RestMethod "http://localhost:8080/api/info")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 2"
$apps = @()
try {
    $apps += Start-App "exp2-customer" "exp2\customer-service" 8081
    $apps += Start-App "exp2-order" "exp2\order-service" 8082
    $value = [ordered]@{
        customer = Invoke-RestMethod "http://localhost:8081/api/customers/1"
        order = Invoke-RestMethod "http://localhost:8082/api/orders/1"
    }
    Write-Result "Experiment 2" $value
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 3"
$apps = @()
try {
    $apps += Start-App "exp3-customer" "exp3\customer-service" 8081
    $apps += Start-App "exp3-order" "exp3\order-service" 8082
    Write-Result "Experiment 3" (Invoke-RestMethod "http://localhost:8082/api/orders/1/customer")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 4"
$apps = @()
try {
    $apps += Start-App "exp4-customer" "exp4\customer-service" 8081
    $apps += Start-App "exp4-order" "exp4\order-service" 8082
    $apps += Start-App "exp4-gateway" "exp4\api-gateway" 8080
    $value = [ordered]@{
        gatewayCustomer = Invoke-RestMethod "http://localhost:8080/api/customers/1"
        gatewayOrder = Invoke-RestMethod "http://localhost:8080/api/orders/1"
    }
    Write-Result "Experiment 4" $value
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 5"
Reset-Rabbit @("order.queue")
$apps = @()
try {
    $apps += Start-App "exp5-order" "exp5\order-service" 8081
    $orderId = Post-Form "http://localhost:8081/orders?product=Veg%20Pizza&quantity=2"
    Start-Sleep -Seconds 1
    $queues = docker exec assignment-rabbitmq rabbitmqctl list_queues name messages
    Write-Result "Experiment 5" "Published order $orderId`r`n$($queues -join "`r`n")"
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 6"
Reset-Rabbit @("order.queue")
$apps = @()
try {
    $apps += Start-App "exp6-payment" "exp6\payment-service" 8080
    $apps += Start-App "exp6-order" "exp6\order-service" 8081
    $orderId = Post-Form "http://localhost:8081/orders?product=Masala%20Dosa&quantity=1"
    Start-Sleep -Seconds 2
    $consumerLog = Get-Content (Join-Path $OutputDirectory "exp6-payment.log") | Select-String "Order ID:|Product:|Quantity:"
    Write-Result "Experiment 6" "Published $orderId`r`n$($consumerLog -join "`r`n")"
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 7"
Reset-Rabbit @("order.queue", "analytics.queue", "notification.queue")
$apps = @()
try {
    $apps += Start-App "exp7-notification" "exp7\notification-service" 8080
    $apps += Start-App "exp7-analytics" "exp7\analytics-service" 8082
    $apps += Start-App "exp7-order" "exp7\order-service" 8081
    $orderId = Post-Form "http://localhost:8081/orders?product=Paneer%20Biryani&quantity=2"
    Start-Sleep -Seconds 2
    $notification = Get-Content (Join-Path $OutputDirectory "exp7-notification.log") | Select-String "Notification|Order ID:|Product:"
    $analytics = Get-Content (Join-Path $OutputDirectory "exp7-analytics.log") | Select-String "Analytics|Order ID:|Product:"
    Write-Result "Experiment 7" "Published $orderId`r`nNotification:`r`n$($notification -join "`r`n")`r`nAnalytics:`r`n$($analytics -join "`r`n")"
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 8"
Reset-Rabbit @("order.processing.queue")
$apps = @()
try {
    $apps += Start-App "exp8-worker" "exp8\order-worker-service" 8084
    $apps += Start-App "exp8-order" "exp8\order-service" 8081
    1..6 | ForEach-Object { Post-Form "http://localhost:8081/orders?product=FoodItem$_&quantity=1" | Out-Null }
    Start-Sleep -Seconds 2
    $workers = Get-Content (Join-Path $OutputDirectory "exp8-worker.log") | Select-String "Worker-[12] processed"
    Write-Result "Experiment 8" ($workers -join "`r`n")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 9"
Reset-Rabbit @("order.retry.queue", "order.dead-letter.queue")
$apps = @()
try {
    $apps += Start-App "exp9-worker" "exp9\order-worker-service" 8084
    $apps += Start-App "exp9-order" "exp9\order-service" 8081
    Post-Form "http://localhost:8081/orders?product=Idli&quantity=2" | Out-Null
    Post-Form "http://localhost:8081/orders?product=FAIL&quantity=1" | Out-Null
    Start-Sleep -Seconds 5
    $retry = Get-Content (Join-Path $OutputDirectory "exp9-worker.log") | Select-String "Processing |successfully|DLQ received"
    Write-Result "Experiment 9" ($retry -join "`r`n")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 10"
$apps = @()
try {
    $apps += Start-App "exp10-order-management" "exp10\order-management" 8080
    $body = @{ customerName="Asha"; address=@{street="MG Road";city="Bengaluru";pincode="560001"}; items=@(@{productName="Veg Thali";quantity=2;price=180}) } | ConvertTo-Json -Depth 5
    Write-Result "Experiment 10" (Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/orders" -ContentType "application/json" -Body $body)
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 11"
$apps = @()
try {
    $apps += Start-App "exp11-domain-event" "exp11\order-domain-events" 8091
    Write-Result "Experiment 11" (Post-Form "http://localhost:8091/orders/place?item=Veg%20Burger&quantity=2")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 12"
$apps = @()
try {
    $apps += Start-App "exp12-payment-handler" "exp12\payment-status-handler" 8092
    Post-Form "http://localhost:8092/orders?orderId=ORD-12&item=Pizza" | Out-Null
    Write-Result "Experiment 12" (Post-Form "http://localhost:8092/orders/ORD-12/payment-completed?amount=499")
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 13"
$apps = @()
try {
    $apps += Start-App "exp13-saga" "exp13\saga-choreography" 8093
    $value = @(
        Post-Form "http://localhost:8093/saga/start?orderId=ORD-13A&amount=450&deliveryAvailable=true"
        Post-Form "http://localhost:8093/saga/start?orderId=ORD-13B&amount=0&deliveryAvailable=true"
        Post-Form "http://localhost:8093/saga/start?orderId=ORD-13C&amount=450&deliveryAvailable=false"
    )
    Write-Result "Experiment 13" $value
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 14"
$apps = @()
try {
    $apps += Start-App "exp14-idempotent" "exp14\idempotent-consumer" 8094
    $body = @{eventId="EVT-100";orderId="ORD-14";eventType="OrderDelivered"} | ConvertTo-Json
    $first = Invoke-RestMethod -Method Post -Uri "http://localhost:8094/events" -ContentType "application/json" -Body $body
    $second = Invoke-RestMethod -Method Post -Uri "http://localhost:8094/events" -ContentType "application/json" -Body $body
    Write-Result "Experiment 14" @($first, $second)
} finally { Stop-Apps $apps }

Write-Host "Running Experiment 15"
$apps = @()
try {
    $apps += Start-App "exp15-contexts" "exp15\bounded-contexts" 8095
    Write-Result "Experiment 15" (Post-Form "http://localhost:8095/contexts/demo?orderId=ORD-15&amount=650")
} finally { Stop-Apps $apps }

Write-Host "All experiments completed. Results: $ResultsFile"
