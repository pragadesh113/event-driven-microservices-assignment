# Event-Driven Microservices Assignment

Basic-level Spring Boot examples for all 15 experiments in the Event-Driven
Microservices assignment. The examples demonstrate REST microservices,
service-to-service communication, API Gateway routing, RabbitMQ messaging,
publish/subscribe, competing consumers, retry and dead-letter handling,
domain events, saga choreography, idempotency, and bounded contexts.

## Main assignment files

- `springboot/springboot/exp1` through `exp15`: source code for all experiments
- `springboot/RUN_ALL_EXPERIMENTS.ps1`: automated build and demo runner
- `springboot/demo-output/latest/results.txt`: verified result summary for all 15 demos
- `deliverables/Event_Driven_Microservices_Runbook.docx`: commands and prerequisites
- `deliverables/Assignment_1_Submission_Event_Driven_Microservices.docx`: formatted submission document

## Quick demo

Install Java 17+, Maven, Docker Desktop, and PowerShell, then run:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\springboot\RUN_ALL_EXPERIMENTS.ps1
```

The runner starts a local RabbitMQ container where required, builds the
projects, executes all 15 demos, and writes the latest results under
`springboot/demo-output/latest`.

For individual commands and expected output, use the Word runbook in the
`deliverables` folder.
