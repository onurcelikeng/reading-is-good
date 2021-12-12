package com.onurcelik.readingisgood.customer.service.impl;

import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.dto.CustomerInput;
import com.onurcelik.readingisgood.customer.dto.CustomerOutput;
import com.onurcelik.readingisgood.customer.entity.Customer;
import com.onurcelik.readingisgood.customer.mapper.CustomerMapper;
import com.onurcelik.readingisgood.customer.repository.CustomerRepository;
import com.onurcelik.readingisgood.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(UUID customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null) {
            throw new BusinessException("Customer not found! Id: " + customerId);
        }
        return customer;
    }

    @Override
    public CustomerOutput getCustomerSummary(UUID customerId) {
        return CustomerMapper.mapperTo(getCustomer(customerId));
    }

    @Override
    @Transactional
    public UUID createCustomer(CustomerInput input) {
        Customer customer = customerRepository.findCustomerByEmail(input.getEmail());
        if(customer != null) {
            throw new BusinessException("E-mail address already used! E-mail: " + input.getEmail());
        }

        Customer newCustomer = customerRepository.save(CustomerMapper.mapperTo(input));
        log.info("Customer is created. ID:" + newCustomer.getId());
        return newCustomer.getId();
    }
}
