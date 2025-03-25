package com.br.foodconnect.service;

import com.br.foodconnect.dto.CustomerAlterDTO;
import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.br.foodconnect.dto.ErrorResponseDTO;
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

    public ResponseEntity<?> registerCustomer(CustomerRegisterDTO dto) {
        try {
            CustomerCredentialModel userCredential = customerCredentialRepository.findByEmail(dto.getEmail());
            CustomerModel customerPhoneNumber = customerRepository.findByPhoneNumber(dto.getPhoneNumber());

            if (userCredential != null && customerPhoneNumber != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este e-mail e este número de telefone já foram cadastrados, por favor forneça outro."));
            } else if (userCredential != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este e-mail já foi cadastrado, por favor forneça outro."));
            } else if (customerPhoneNumber != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO("Este número de telefone já foi cadastrado, por favor forneça outro."));
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
