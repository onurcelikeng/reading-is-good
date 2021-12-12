package com.onurcelik.readingisgood.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public final class AuthOutput implements Serializable {

    private String token;
}
