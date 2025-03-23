package com.br.foodconnect.model;

import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_credential_id")
    private CustomerCredentialModel credential;

    public CustomerModel(CustomerRegisterDTO dto) {
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
    }

    public void setCredential(CustomerCredentialModel credential) {
        this.credential = credential;
    }
}
