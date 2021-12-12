package com.onurcelik.readingisgood.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
public final class AuthInput implements Serializable {

    @NotBlank(message = "Kullanıcı adınızı giriniz.")
    private String username;

    @NotBlank(message = "Şifrenizi giriniz.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;
}
