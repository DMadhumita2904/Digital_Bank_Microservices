package com.digitalbank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String accountNumber;
    private Double balance;

    // Transient fields to hold customer info
    @Transient
    private String firstName;

    @Transient
    private String lastName;

    @Transient
    private String email;

    @Transient
    private String phoneNumber;
}
