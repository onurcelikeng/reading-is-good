package com.onurcelik.readingisgood.address.controller;

import com.onurcelik.readingisgood.address.dto.AddressInput;
import com.onurcelik.readingisgood.address.service.AddressService;
import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Address")
@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseDTO<UUID> createAddress(@RequestBody @Valid AddressInput input) throws BusinessException {
        UUID addressId = addressService.createAddress(input);
        return new ResponseDTO<>(HttpStatus.CREATED, addressId);
    }

    @PostMapping("/{addressId}")
    public ResponseDTO<UUID> updateAddress(@PathVariable("addressId") UUID addressId, @RequestBody @Valid AddressInput input) throws BusinessException {
        UUID id = addressService.updateAddress(addressId, input);
        return new ResponseDTO<>(HttpStatus.OK, id);
    }

    @DeleteMapping("/{addressId}")
    public ResponseDTO<UUID> deleteAddress(@PathVariable("addressId") UUID addressId) throws BusinessException {
        UUID id = addressService.deleteAddress(addressId);
        return new ResponseDTO<>(HttpStatus.OK, id);
    }
}
