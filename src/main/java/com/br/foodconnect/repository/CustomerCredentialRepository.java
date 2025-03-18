package com.br.foodconnect.repository;

import com.br.foodconnect.model.CustomerCredentialModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCredentialRepository extends JpaRepository<CustomerCredentialModel, Long> {

    CustomerCredentialModel findByEmail(String email);
}
