package com.br.foodconnect.service;

import com.br.foodconnect.dto.*;
import com.br.foodconnect.model.CustomerCredentialModel;
import com.br.foodconnect.model.CustomerModel;
import com.br.foodconnect.repository.CustomerCredentialRepository;
import com.br.foodconnect.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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
            customerCredentialModel.setHasAcceptedTerms(Boolean.TRUE);

            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            Date date = Date.from(zonedDateTime.toInstant());
            customerCredentialModel.setTermsAcceptedAt(date);

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

    public ResponseEntity<CustomerUpdateDTO> updateCustomer(CustomerUpdateDTO dto) throws ParseException {

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

        customerRepository.save(customerModel);

        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<?> updatePassword(UpdatePasswordDTO dto) {
        System.out.println("Procurando usuário com o email: " + dto.getEmail());
        CustomerCredentialModel customerCredentialModel = customerCredentialRepository.findByEmail(dto.getEmail());

        if (customerCredentialModel == null) {
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        // Verificação se a nova senha é válida (mínimo de caracteres ou qualquer outra regra)
        if (dto.getPassword().length() < 6) {
            return new ResponseEntity<>("A nova senha deve ter pelo menos 6 caracteres", HttpStatus.BAD_REQUEST);
        }

        // Verificação se a nova senha é igual à senha atual
        if (dto.getPassword().equals(customerCredentialModel.getPassword())) {
            return new ResponseEntity<>("A nova senha não pode ser a mesma que a senha atual", HttpStatus.BAD_REQUEST);
        }

        // Criptografar a nova senha
        String encryptedPassword = passwordService.criptografar(dto.getPassword());

        // Atualizar a senha
        customerCredentialModel.setPassword(encryptedPassword);

        try {
            customerCredentialRepository.save(customerCredentialModel);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar a senha", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Senha atualizada com sucesso", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        CustomerCredentialModel customerCredentialModel = customerCredentialRepository.findByEmail(resetPasswordDTO.email());

        if(customerCredentialModel == null){
            return new ResponseEntity<>("Credential not found", HttpStatus.NOT_FOUND);
        }

        if(passwordService.verificarSenha(resetPasswordDTO.password(), customerCredentialModel.getPassword())){
            return new ResponseEntity<>("The new password cannot be the same as the old password", HttpStatus.CONFLICT);
        }

        try{
            customerCredentialModel.setPassword(passwordService.criptografar(resetPasswordDTO.password()));
            customerCredentialRepository.save(customerCredentialModel);
            return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Have a unexpected error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

