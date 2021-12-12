package com.onurcelik.readingisgood.address.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AddressInput implements Serializable {

    private UUID customerId;

    @NotBlank(message = "Adres başlığını giriniz. Örnek: Ev Adresi")
    private String title;

    @NotBlank(message = "Adres bilgisini giriniz.")
    private String address;

    @NotBlank(message = "Sokak Numarası bilgisini giriniz.")
    private String streetNumber;

    @NotBlank(message = "Kat bilgisini giriniz.")
    private String floor;

    @NotBlank(message = "Daire bilgisini giriniz.")
    private String flat;

    @NotBlank(message = "İl bilgisini giriniz.")
    private String city;

    @NotBlank(message = "İlçe bilgisini giriniz.")
    private String town;
}
