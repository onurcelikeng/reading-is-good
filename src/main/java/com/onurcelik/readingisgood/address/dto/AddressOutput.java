package com.onurcelik.readingisgood.address.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressOutput implements Serializable {

    private String title;
    private String address;
    private String streetNumber;
    private String floor;
    private String flat;
    private String city;
    private String town;
}
