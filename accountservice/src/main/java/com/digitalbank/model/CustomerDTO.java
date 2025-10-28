package com.digitalbank.model;

import lombok.Data;

// Minimal DTO for mapping Customer Service response
@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
