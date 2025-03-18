package com.br.foodconnect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerRegisterDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
}
