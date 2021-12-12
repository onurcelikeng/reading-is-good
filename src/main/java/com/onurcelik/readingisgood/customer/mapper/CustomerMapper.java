package com.onurcelik.readingisgood.customer.mapper;

import com.onurcelik.readingisgood.customer.dto.CustomerInput;
import com.onurcelik.readingisgood.customer.dto.CustomerOutput;
import com.onurcelik.readingisgood.customer.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {

    }

    public static CustomerOutput mapperTo(Customer customer) {
        String fullName = customer.getFirstName() + " " + (customer.getMiddleName() == null ? "" : (customer.getMiddleName() + " "))  + customer.getLastName();
        return CustomerOutput.builder()
                .fullName(fullName)
                .email(customer.getEmail())
                .phone(customer.getMsisdn())
                .build();
    }

    public static Customer mapperTo(CustomerInput input) {
        return new Customer(input.getFirstName(), input.getMiddleName(), input.getLastName(), input.getEmail(), input.getPhone());
    }
}
