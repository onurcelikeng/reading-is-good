package com.onurcelik.readingisgood.customer.controller;

import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.customer.dto.CustomerInput;
import com.onurcelik.readingisgood.customer.service.CustomerService;
import com.onurcelik.readingisgood.order.entity.Order;
import com.onurcelik.readingisgood.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Customer")
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public final class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderService orderService;

    @PostMapping
    public ResponseDTO<UUID> createCustomer(@RequestBody @Valid CustomerInput input) throws BusinessException {
        UUID customerId = customerService.createCustomer(input);
        return new ResponseDTO<>(HttpStatus.CREATED, customerId);
    }

    @GetMapping("/{customerId}/addresses")
    public ResponseDTO<List<AddressOutput>> getAddressesByCustomerId(@PathVariable("customerId") UUID customerId) {
        return new ResponseDTO<>(HttpStatus.OK, addressService.getAddressesByCustomer(customerId));
    }

    @GetMapping("/{customerId}/orders")
    public ResponseDTO<List<Order>> getOrdersByCustomerId(@PathVariable("customerId") UUID customerId,
                                                          @RequestParam(defaultValue = "0") int pageNumber,
                                                          @RequestParam(defaultValue = "2") int pageSize) {
        return new ResponseDTO<>(HttpStatus.OK, orderService.getOrdersByCustomer(customerId, pageNumber, pageSize));
    }
}
