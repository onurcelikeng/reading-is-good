package com.onurcelik.readingisgood.customer.service;

import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.dto.CustomerInput;
import com.onurcelik.readingisgood.customer.dto.CustomerOutput;
import com.onurcelik.readingisgood.customer.entity.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer getCustomer(UUID customerId);

    CustomerOutput getCustomerSummary(UUID customerId);

    UUID createCustomer(CustomerInput input) throws BusinessException;
}
