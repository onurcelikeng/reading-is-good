package com.onurcelik.readingisgood.customer.repository;

import com.onurcelik.readingisgood.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {

    Customer findCustomerById(UUID id);

    Customer findCustomerByEmail(String email);
}
