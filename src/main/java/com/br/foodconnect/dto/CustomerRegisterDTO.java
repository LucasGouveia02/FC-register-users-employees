package com.br.foodconnect.dto;



public class CustomerRegisterDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

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
