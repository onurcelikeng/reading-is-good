package com.onurcelik.readingisgood.customer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document("t_customers")
public final class Customer {

    @Id
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @NotNull(message = "First name is mandatory")
    private String firstName;

    private String middleName;

    @NotNull(message = "Last name is mandatory")
    private String lastName;

    @NotNull
    @Email(message = "Enter valid email")
    private String email;

    private String msisdn;

    public Customer(String firstName, String middleName, String lastName, String email, String msisdn) {
        LocalDateTime now = LocalDateTime.now();

        this.id = UUID.randomUUID();
        this.createDate = now;
        this.modifyDate = now;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.msisdn = msisdn;
    }
}
