package com.br.foodconnect.service;

import com.br.foodconnect.dto.*;
import com.br.foodconnect.model.*;
import com.br.foodconnect.repository.EmployeeCredentialRepository;
import com.br.foodconnect.repository.EmployeeRepository;
import com.br.foodconnect.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCredentialRepository employeeCredentialRepository;

    @Autowired
    private StoreRepository storeRepository;

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

            StoreModel store = storeRepository.findById(dto.getStoreId()).orElseThrow(() -> new RuntimeException("Loja não encontrada com ID: " + dto.getStoreId()));

            employee.setStore(store);

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
    public ResponseEntity<EmployeeDTO> getEmployeeById(Long id) {
        // Busca o EmployeeModel pelo id
        EmployeeModel employeeModel = employeeRepository.findById(id).orElse(null);

        if (employeeModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeModel);
        return ResponseEntity.ok(employeeDTO);
    }
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }
}
