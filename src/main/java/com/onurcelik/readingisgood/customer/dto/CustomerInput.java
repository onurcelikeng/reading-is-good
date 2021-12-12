package com.onurcelik.readingisgood.customer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class CustomerInput {

    @NotBlank(message = "İsim bilgisini giriniz.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Soyisim bilgisini giriniz.")
    private String lastName;

    @NotBlank(message = "E-mail bilgisini giriniz.")
    @Email(message = "Doğru format giriniz. Örnek: abc@xyz.com")
    private String email;

    @NotBlank(message = "Telefon bilgisini giriniz.")
    private String phone;
}
