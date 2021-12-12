package com.onurcelik.readingisgood.address.service.impl;

import com.onurcelik.readingisgood.address.dto.AddressInput;
import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.address.entity.Address;
import com.onurcelik.readingisgood.address.repository.AddressRepository;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.onurcelik.readingisgood.address.constant.AddressConstant.STATUS_ACTIVE;
import static com.onurcelik.readingisgood.address.constant.AddressConstant.STATUS_DELETED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository mockAddressRepository;

    private AddressService addressService;

    private AddressInput input;

    @BeforeEach
    void setup() {
        addressService = new AddressServiceImpl(mockAddressRepository);

        UUID customerId = UUID.randomUUID();
        String title = "Ev Adresi";
        String addressDescription = "Kemal Paşa Mahallesi, Cumhuriyet Sokak";
        String streetNumber = "4";
        String floor = "3";
        String flat = "24";
        String city = "İstanbul";
        String town = "Kadıköy";

        input = new AddressInput();
        input.setCustomerId(customerId);
        input.setTitle(title);
        input.setAddress(addressDescription);
        input.setStreetNumber(streetNumber);
        input.setFloor(floor);
        input.setFlat(flat);
        input.setCity(city);
        input.setTown(town);
    }

    @Test
    void testGetAddressesByCustomer_valid_returnAddressOutputList() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown()));

        // Given
        when(mockAddressRepository.findAllAddressByCustomerId(input.getCustomerId())).thenReturn(addressList);

        // When
        List<AddressOutput> addressOutputs = addressService.getAddressesByCustomer(input.getCustomerId());

        // Then
        Assertions.assertEquals(1, addressOutputs.size());
    }

    @Test
    void testGetAddressesByCustomer_addressNotExist_throwsException() {
        UUID customerId = UUID.randomUUID();
        List<Address> emptyList = new ArrayList<>();

        // Given
        when(mockAddressRepository.findAllAddressByCustomerId(customerId)).thenReturn(emptyList);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.getAddressesByCustomer(customerId));
    }

    @Test
    void testGetAddress_valid_returnAddressOutput() {
        UUID addressId = UUID.randomUUID();
        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());

        // Given
        when(mockAddressRepository.findAddressByAddressId(addressId)).thenReturn(address);

        // When
        AddressOutput addressOutput = addressService.getAddress(addressId);

        // Then
        Assertions.assertNotNull(addressOutput);
    }

    @Test
    void testGetAddress_addressNotExist_throwsException() {
        UUID addressId = UUID.randomUUID();

        // Given
        when(mockAddressRepository.findAddressByAddressId(addressId)).thenReturn(null);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.getAddress(addressId));
    }

    @Test
    void testCreateAddress_valid_returnUUID() {
        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());
        when(mockAddressRepository.save(any())).thenReturn(address);

        UUID response = addressService.createAddress(input);

        Assertions.assertEquals(address.getAddressId(), response);
    }

    @Test
    void testUpdateAddress_valid_returnUUID() {
        UUID addressId = UUID.randomUUID();
        input.setTitle("İş Adresi");

        // Given
        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());
        when(mockAddressRepository.findAddressByAddressIdAndStatus(addressId, STATUS_ACTIVE)).thenReturn(address);

        // When
        UUID response = addressService.updateAddress(addressId, input);

        // Then
        Assertions.assertEquals(address.getAddressId(), response);
    }

    @Test
    void testUpdateAddress_addressNotExist_throwsException() {
        UUID addressId = UUID.randomUUID();

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndStatus(addressId, STATUS_ACTIVE)).thenReturn(null);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.updateAddress(addressId, input));
    }

    @Test
    void testDeleteAddress_valid_returnUUID() {
        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());
        address.setStatus(STATUS_DELETED);
        address.setModifyDate(LocalDateTime.now());

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndStatus(address.getAddressId(), STATUS_ACTIVE)).thenReturn(address);
        when(mockAddressRepository.save(address)).thenReturn(address);

        // When
        UUID response = addressService.deleteAddress(address.getAddressId());

        // Then
        Assertions.assertEquals(address.getAddressId(), response);
        Assertions.assertEquals(STATUS_DELETED, address.getStatus());
    }

    @Test
    void testDeleteAddress_addressNotExist_throwsException() {
        UUID addressId = UUID.randomUUID();

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndStatus(addressId, STATUS_ACTIVE)).thenReturn(null);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.deleteAddress(addressId));
    }

    @Test
    void testGetAddressByCustomer_addressNotExist_throwsException() {
        UUID addressId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndCustomerId(addressId, customerId)).thenReturn(null);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.getAddressByCustomer(addressId, customerId));
    }

    @Test
    void testGetAddressByCustomer_addressStatusDeleted_throwsException() {
        UUID addressId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());
        address.setStatus(STATUS_DELETED);
        address.setModifyDate(LocalDateTime.now());

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndCustomerId(addressId, customerId)).thenReturn(address);

        // Then
        Assertions.assertThrows(BusinessException.class, () -> addressService.getAddressByCustomer(addressId, customerId));
    }

    @Test
    void testGetAddressByCustomer_valid_returnAddress() {
        UUID addressId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Address address = new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());

        // Given
        when(mockAddressRepository.findAddressByAddressIdAndCustomerId(addressId, customerId)).thenReturn(address);

        // When
        Address response = addressService.getAddressByCustomer(addressId, customerId);

        // Then
        Assertions.assertEquals(address.getAddressId(), response.getAddressId());
    }
}