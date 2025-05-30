package com.br.foodconnect.dto.request;

import com.br.foodconnect.model.CustomerCredentialModel;
import com.br.foodconnect.model.CustomerModel;

public class CustomerRegisterDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;


    public CustomerRegisterDTO() {
    }

    public CustomerRegisterDTO(CustomerModel customerModel, CustomerCredentialModel customerCredentialModel) {
        this.name = customerModel.getName();
        this.phoneNumber = customerModel.getPhoneNumber();
        this.email = customerCredentialModel.getEmail();
        this.password = "";
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

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
