package com.onurcelik.readingisgood.address.service;

import com.onurcelik.readingisgood.address.dto.AddressInput;
import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.address.entity.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<AddressOutput> getAddressesByCustomer(UUID customerId);

    AddressOutput getAddress(UUID id);

    UUID createAddress(AddressInput input);

    UUID updateAddress(UUID id, AddressInput input);

    UUID deleteAddress(UUID id);

    Address getAddressByCustomer(UUID addressId, UUID customerId);
}
