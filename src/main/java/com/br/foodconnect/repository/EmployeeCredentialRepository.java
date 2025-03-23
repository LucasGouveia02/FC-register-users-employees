package com.br.foodconnect.repository;

import com.br.foodconnect.model.EmployeeCredentialModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCredentialRepository extends JpaRepository <EmployeeCredentialModel, Long> {
    EmployeeCredentialModel findByEmail(String email);
}
