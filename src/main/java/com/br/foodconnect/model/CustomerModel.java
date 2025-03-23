package com.br.foodconnect.model;

import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerCredentialModel getCredential() {
        return credential;
    }

    public void setCredential(CustomerCredentialModel credential) {
        this.credential = credential;
    }

    public CustomerModel() {
    }
}
