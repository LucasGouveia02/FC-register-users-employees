package com.br.foodconnect.controller;

import com.br.foodconnect.dto.CustomerAlterDTO;
import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.br.foodconnect.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterDTO dto) throws ParseException {
        return customerService.registerCustomer(dto);
    }

    @PutMapping("/alter")
    public ResponseEntity<CustomerAlterDTO> alterCustomer(@RequestBody CustomerAlterDTO dto) throws ParseException {
        return customerService.alterCustomer(dto);
    }
}
