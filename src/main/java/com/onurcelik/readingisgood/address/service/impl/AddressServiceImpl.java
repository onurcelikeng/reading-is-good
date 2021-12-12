package com.onurcelik.readingisgood.address.service.impl;

import com.onurcelik.readingisgood.address.dto.AddressInput;
import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.address.entity.Address;
import com.onurcelik.readingisgood.address.mapper.AddressMapper;
import com.onurcelik.readingisgood.address.repository.AddressRepository;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.onurcelik.readingisgood.address.constant.AddressConstant.STATUS_ACTIVE;
import static com.onurcelik.readingisgood.address.constant.AddressConstant.STATUS_DELETED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<AddressOutput> getAddressesByCustomer(UUID customerId) {
        List<Address> addresses = addressRepository.findAllAddressByCustomerId(customerId);
        if(addresses.isEmpty()) {
            throw new BusinessException("Müşteriye ait kayıtlı adres bulunamadı. CustomerId: " + customerId);
        }
        return AddressMapper.mapperTo(addresses);
    }

    @Override
    public AddressOutput getAddress(UUID addressId) {
        Address address = addressRepository.findAddressByAddressId(addressId);
        if(address == null) {
            throw new BusinessException("Address not found! Id: " + addressId);
        }
        return AddressMapper.mapperTo(address);
    }

    @Override
    @Transactional
    public UUID createAddress(AddressInput input) {
        Address address = addressRepository.save(AddressMapper.mapperTo(input));
        log.info("Address is created. Id:" + address.getAddressId());

        return address.getAddressId();
    }

    @Override
    @Transactional
    public UUID updateAddress(UUID addressId, AddressInput input) {
        Address address = addressRepository.findAddressByAddressIdAndStatus(addressId, STATUS_ACTIVE);
        if(address == null) {
            throw new BusinessException("Address not found!");
        }

        address.setTitle(input.getTitle());
        address.setAddress(input.getAddress());
        address.setStreetNumber(input.getStreetNumber());
        address.setFloor(input.getFloor());
        address.setFlat(input.getFlat());
        address.setCity(input.getCity());
        address.setTown(input.getTown());
        address.setModifyDate(LocalDateTime.now());
        addressRepository.save(address);
        log.info("Address is updated. ID:" + address.getAddressId());

        return address.getAddressId();
    }

    @Override
    @Transactional
    public UUID deleteAddress(UUID addressId) {
        Address address = addressRepository.findAddressByAddressIdAndStatus(addressId, STATUS_ACTIVE);
        if(address == null) {
            throw new BusinessException("Address not found!");
        }

        address.setStatus(STATUS_DELETED);
        address.setModifyDate(LocalDateTime.now());
        addressRepository.save(address);
        log.info("Address is deleted. ID:" + address.getAddressId());

        return address.getAddressId();
    }

    @Override
    public Address getAddressByCustomer(UUID addressId, UUID customerId) {
        Address address = addressRepository.findAddressByAddressIdAndCustomerId(addressId, customerId);
        if(address == null) {
            throw new BusinessException("Müşteriye kayıtlı adres bulunamadı.");
        }
        if(!STATUS_ACTIVE.equals(address.getStatus())) {
            throw new BusinessException("Kayıtlı adresin statüsü aktif değildir.");
        }
        return address;
    }
}
