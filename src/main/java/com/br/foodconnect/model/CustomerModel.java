package com.br.foodconnect.model;

import com.br.foodconnect.dto.request.CustomerRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_credential_id")
    private CustomerCredentialModel credential;

    public CustomerModel() {
    }

    public CustomerModel(CustomerRegisterDTO dto) {
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
