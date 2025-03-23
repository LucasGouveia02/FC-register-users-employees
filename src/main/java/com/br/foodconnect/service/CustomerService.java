package com.br.foodconnect.service;

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
            customerCredentialModel.setEnabled(Boolean.TRUE);
            CustomerCredentialModel savedCustomerCredentialModel = customerCredentialRepository.save(customerCredentialModel);

            CustomerModel customer = new CustomerModel(dto);
            customer.setCredential(savedCustomerCredentialModel);
            CustomerModel customersaved = customerRepository.save(customer);
            CustomerRegisterDTO customerRegisterDTO = new CustomerRegisterDTO(customersaved, savedCustomerCredentialModel);

            return new ResponseEntity<>(customerRegisterDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
