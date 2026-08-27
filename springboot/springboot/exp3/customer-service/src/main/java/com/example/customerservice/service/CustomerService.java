package com.example.customerservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.customerservice.model.Customer;

@Service
public class CustomerService {

    private final List<Customer> customers = new ArrayList<>();

    public CustomerService() {

        customers.add(new Customer(
            1,
            "User1",
            "user1@example.com",
            "0000000001"
        ));

        customers.add(new Customer(
            2,
            "User2",
            "user2@example.com",
            "0000000002"
        ));
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(int id) {

        for (Customer customer : customers) {

            if (customer.getId() == id) {
                return customer;
            }
        }

        return null;
    }

    public Customer addCustomer(Customer customer) {

        customers.add(customer);
        return customer;
    }

    public Customer updateCustomer(int id, Customer updatedCustomer) {

        for (Customer customer : customers) {

            if (customer.getId() == id) {

                customer.setName(updatedCustomer.getName());
                customer.setEmail(updatedCustomer.getEmail());
                customer.setPhone(updatedCustomer.getPhone());

                return customer;
            }
        }

        return null;
    }

    public boolean deleteCustomer(int id) {

        return customers.removeIf(
                customer -> customer.getId() == id
        );
    }
}