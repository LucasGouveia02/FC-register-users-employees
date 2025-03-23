package com.br.foodconnect.dto;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
  
    public void setPassword(String password) {
        this.password = password;
    }
}
