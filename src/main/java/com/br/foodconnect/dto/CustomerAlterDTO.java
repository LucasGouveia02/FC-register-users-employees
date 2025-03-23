package com.br.foodconnect.dto;

public class CustomerAlterDTO {
    private String name;
    private String phoneNumber;
    private String password;
    private String email;

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

    public String getPasssword() {
        return password;
    }

    public void setPasssword(String passsword) {
        this.password = passsword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
