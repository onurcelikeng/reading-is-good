package com.onurcelik.readingisgood.address.mapper;

import com.onurcelik.readingisgood.address.dto.AddressInput;
import com.onurcelik.readingisgood.address.dto.AddressOutput;
import com.onurcelik.readingisgood.address.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {

    private AddressMapper() {

    }

    public static List<AddressOutput> mapperTo(List<Address> addresses) {
        List<AddressOutput> addressOutputs = new ArrayList<>();
        for(Address address : addresses) {
            addressOutputs.add(mapperTo(address));
        }
        return addressOutputs;
    }

    public static AddressOutput mapperTo(Address address) {
        return AddressOutput.builder()
                .title(address.getTitle())
                .address(address.getAddress())
                .streetNumber(address.getStreetNumber())
                .floor(address.getFloor())
                .flat(address.getFlat())
                .city(address.getCity())
                .town(address.getTown())
                .build();
    }

    public static Address mapperTo(AddressInput input) {
        return new Address(input.getCustomerId(), input.getTitle(), input.getAddress(), input.getStreetNumber(), input.getFloor(), input.getFlat(), input.getCity(), input.getTown());
    }
}
