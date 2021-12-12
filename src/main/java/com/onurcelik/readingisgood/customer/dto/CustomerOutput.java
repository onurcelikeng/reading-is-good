package com.onurcelik.readingisgood.customer.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOutput implements Serializable {

    private String fullName;
    private String email;
    private String phone;
}
