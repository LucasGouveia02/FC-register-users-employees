package com.br.foodconnect.repository;

import com.br.foodconnect.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {
    EmployeeModel findByPhoneNumber(String phoneNumber);
}
