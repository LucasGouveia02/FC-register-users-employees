package com.br.foodconnect.service;

import com.br.foodconnect.dto.CustomerAlterDTO;
import com.br.foodconnect.dto.EmployeeAlterDTO;
import com.br.foodconnect.dto.EmployeeRegisterDTO;
import com.br.foodconnect.dto.ErrorResponseDTO;
import com.br.foodconnect.model.CustomerCredentialModel;
import com.br.foodconnect.model.CustomerModel;
import com.br.foodconnect.model.EmployeeCredentialModel;
import com.br.foodconnect.model.EmployeeModel;
import com.br.foodconnect.repository.EmployeeCredentialRepository;
import com.br.foodconnect.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCredentialRepository employeeCredentialRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity registerEmployee(EmployeeRegisterDTO dto) {
        try {
            EmployeeCredentialModel employeeCredential = employeeCredentialRepository.findByEmail(dto.getEmail());
            EmployeeModel employeePhoneNumber = employeeRepository.findByPhoneNumber(dto.getPhoneNumber());

            if (employeeCredential != null && employeePhoneNumber != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este e-mail e este número de telefone já foram cadastrados, por favor forneça outro."));
            } else if (employeeCredential != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este e-mail já foi cadastrado, por favor forneça outro."));
            } else if (employeePhoneNumber != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este número de telefone já foi cadastrado, por favor forneça outro."));
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
    public ResponseEntity<EmployeeModel> alterEmployee(Long id, EmployeeAlterDTO dto) throws ParseException {
        EmployeeModel employeeModel = employeeRepository.findById(id).orElse(null);

        if (employeeModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        EmployeeCredentialModel employeeCredential = employeeModel.getCredential();

        employeeModel.setName(dto.getName());
        employeeCredential.setRole(dto.getRole());
        employeeModel.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            employeeCredential.setPassword(passwordService.criptografar(dto.getPassword()));
            employeeCredentialRepository.save(employeeCredential);
        }

        employeeRepository.save(employeeModel);

        return ResponseEntity.ok(employeeModel);
    }
    public ResponseEntity<EmployeeModel> getEmployeeById(Long id) {
        // Busca o EmployeeModel pelo id
        EmployeeModel employeeModel = employeeRepository.findById(id).orElse(null);

        if (employeeModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(employeeModel);
    }
}
