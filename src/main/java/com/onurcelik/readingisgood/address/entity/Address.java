package com.onurcelik.readingisgood.address.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.onurcelik.readingisgood.address.constant.AddressConstant.STATUS_ACTIVE;

@Getter
@Setter
@Document("t_address")
public final class Address {

    @Id
    private UUID addressId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String status;

    @NotNull
    private UUID customerId;

    private String title; // home or work
    private String address;
    private String streetNumber;
    private String floor;
    private String flat;
    private String city;
    private String town;

    public Address(UUID customerId, String title, String address, String streetNumber, String floor, String flat, String city, String town) {
        LocalDateTime now = LocalDateTime.now();

        this.addressId = UUID.randomUUID();
        this.createDate = now;
        this.modifyDate = now;
        this.status = STATUS_ACTIVE;
        this.customerId = customerId;
        this.title = title;
        this.address = address;
        this.streetNumber = streetNumber;
        this.floor = floor;
        this.flat = flat;
        this.city = city;
        this.town = town;
    }
}
