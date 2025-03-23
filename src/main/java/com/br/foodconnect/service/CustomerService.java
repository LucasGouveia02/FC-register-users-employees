package com.br.foodconnect.service;

import com.br.foodconnect.dto.CustomerAlterDTO;
import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.br.foodconnect.model.CustomerCredentialModel;
import com.br.foodconnect.model.CustomerModel;
import com.br.foodconnect.repository.CustomerCredentialRepository;
import com.br.foodconnect.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerCredentialRepository customerCredentialRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<CustomerRegisterDTO> registerCustomer(CustomerRegisterDTO dto) throws ParseException {
        try {
            CustomerCredentialModel userCredential = customerCredentialRepository.findByEmail(dto.getEmail());

            if (userCredential != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            CustomerCredentialModel customerCredentialModel = new CustomerCredentialModel();
            customerCredentialModel.setEmail(dto.getEmail());
            customerCredentialModel.setPassword(passwordService.criptografar(dto.getPassword()));
            CustomerCredentialModel savedCustomerCredentialModel = customerCredentialRepository.save(customerCredentialModel);

            CustomerModel customer = new CustomerModel(dto);
            customer.setCredential(savedCustomerCredentialModel);
            customerRepository.save(customer);

            dto.setPassword("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<CustomerAlterDTO> alterCustomer(CustomerAlterDTO dto) throws ParseException {

        CustomerCredentialModel userCredential = customerCredentialRepository.findByEmail(dto.getEmail());
        if (userCredential == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CustomerModel customerModel = customerRepository.findByCredential(userCredential);
        if (customerModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        customerModel.setName(dto.getName());
        customerModel.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPasssword() != null && !dto.getPasssword().isEmpty()) {
            userCredential.setPassword(passwordService.criptografar(dto.getPasssword()));
            customerCredentialRepository.save(userCredential);
        }


        customerRepository.save(customerModel);

        return ResponseEntity.ok(dto);
    }
}
