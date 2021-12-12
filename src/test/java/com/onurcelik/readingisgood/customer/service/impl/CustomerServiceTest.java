package com.onurcelik.readingisgood.customer.service.impl;

import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.dto.CustomerInput;
import com.onurcelik.readingisgood.customer.dto.CustomerOutput;
import com.onurcelik.readingisgood.customer.entity.Customer;
import com.onurcelik.readingisgood.customer.repository.CustomerRepository;
import com.onurcelik.readingisgood.customer.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository mockCustomerRepository;

    private CustomerService customerService;

    private CustomerInput input;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(mockCustomerRepository);

        input = new CustomerInput();
        input.setFirstName("Onur");
        input.setLastName("Celik");
        input.setEmail("test@gmail.com");
        input.setPhone("05064837685");
    }

    @Test
    void testGetCustomer_valid_returnCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(input.getFirstName(), input.getMiddleName(), input.getLastName(), input.getEmail(), input.getPhone());

        when(mockCustomerRepository.findCustomerById(customerId)).thenReturn(customer);

        Customer response = customerService.getCustomer(customerId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(customer.getId(), response.getId());
    }

    @Test
    void testGetCustomer_customerNotExist_throwsException() {
        UUID customerId = UUID.randomUUID();

        when(mockCustomerRepository.findCustomerById(customerId)).thenReturn(null);

        Assertions.assertThrows(BusinessException.class, () -> customerService.getCustomer(customerId));
    }

    @Test
    void testGetCustomerSummary_valid_returnCustomerOutput() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(input.getFirstName(), input.getMiddleName(), input.getLastName(), input.getEmail(), input.getPhone());

        when(mockCustomerRepository.findCustomerById(customerId)).thenReturn(customer);

        CustomerOutput response = customerService.getCustomerSummary(customerId);
        Assertions.assertEquals(customer.getEmail(), response.getEmail());
    }

    @Test
    void testCreateCustomer_valid_returnUUID() {
        Customer customer = new Customer(input.getFirstName(), input.getMiddleName(), input.getLastName(), input.getEmail(), input.getPhone());

        when(mockCustomerRepository.findCustomerByEmail(input.getEmail())).thenReturn(null);
        when(mockCustomerRepository.save(any())).thenReturn(customer);

        UUID response = customerService.createCustomer(input);
        Assertions.assertEquals(customer.getId(), response);
    }

    @Test
    void testCreateCustomer_customerAlreadyExist_throwsException() {
        String email = "test@gmail.com";
        Customer customer = new Customer(input.getFirstName(), input.getMiddleName(), input.getLastName(), input.getEmail(), input.getPhone());

        when(mockCustomerRepository.findCustomerByEmail(email)).thenReturn(customer);

        Assertions.assertThrows(BusinessException.class, () -> customerService.createCustomer(input));
    }
}