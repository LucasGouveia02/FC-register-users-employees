package com.br.foodconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee-credential")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCredentialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Boolean isEnabled;
    private String role;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
