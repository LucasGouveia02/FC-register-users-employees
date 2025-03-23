package com.br.foodconnect.service;

import com.br.foodconnect.dto.EmployeeRegisterDTO;
import com.br.foodconnect.model.EmployeeCredentialModel;
import com.br.foodconnect.model.EmployeeModel;
import com.br.foodconnect.repository.EmployeeCredentialRepository;
import com.br.foodconnect.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCredentialRepository employeeCredentialRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity registerEmployee(EmployeeRegisterDTO dto) {
        try {
            EmployeeCredentialModel userCredential = employeeCredentialRepository.findByEmail(dto.getEmail());

            if (userCredential != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            EmployeeCredentialModel employeeCredentialModel = new EmployeeCredentialModel();
            employeeCredentialModel.setEmail(dto.getEmail());
            employeeCredentialModel.setPassword(passwordService.criptografar(dto.getPassword()));
            employeeCredentialModel.setRole(dto.getRole());
            employeeCredentialModel.setEnabled(Boolean.TRUE);
            EmployeeCredentialModel savedEmployeeCredentialModel = employeeCredentialRepository.save(employeeCredentialModel);

            EmployeeModel employee = new EmployeeModel(dto);
            employee.setCredential(savedEmployeeCredentialModel);
            employeeRepository.save(employee);

            dto.setPassword("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
