package com.br.foodconnect.repository;

import com.br.foodconnect.model.CustomerCredentialModel;
import com.br.foodconnect.model.CustomerModel;
import com.br.foodconnect.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    CustomerModel findByCredential(CustomerCredentialModel credential);
    CustomerModel findByPhoneNumber(String phoneNumber);
}
