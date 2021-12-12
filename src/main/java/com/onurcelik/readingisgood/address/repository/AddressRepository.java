package com.onurcelik.readingisgood.address.repository;

import com.onurcelik.readingisgood.address.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends MongoRepository<Address, UUID> {

    Address findAddressByAddressId(UUID addressId);

    Address findAddressByAddressIdAndStatus(UUID addressId, String status);

    List<Address> findAllAddressByCustomerId(UUID customerId);

    Address findAddressByAddressIdAndCustomerId(UUID addressId, UUID customerId);
}
