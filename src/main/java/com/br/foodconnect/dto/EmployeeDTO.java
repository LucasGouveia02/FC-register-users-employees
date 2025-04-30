package com.br.foodconnect.dto;

import com.br.foodconnect.model.EmployeeModel;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;

    public EmployeeDTO(EmployeeModel employeeModel) {
        this.id = employeeModel.getId();
        this.name = employeeModel.getName();
        this.email = employeeModel.getCredential().getEmail();
        this.phoneNumber = employeeModel.getPhoneNumber();
        this.role = employeeModel.getCredential().getRole();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
