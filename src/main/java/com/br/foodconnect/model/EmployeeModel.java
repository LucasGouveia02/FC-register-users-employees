package com.br.foodconnect.model;

import com.br.foodconnect.dto.EmployeeRegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "employee_credential_id")
    private EmployeeCredentialModel credential;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreModel store;

    public EmployeeModel(EmployeeRegisterDTO dto) {
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
    }

    public void setCredential(EmployeeCredentialModel credential) {
        this.credential = credential;
    }
}
