package com.br.foodconnect.controller;

import com.br.foodconnect.dto.CustomerUpdateDTO;
import com.br.foodconnect.dto.CustomerRegisterDTO;
import com.br.foodconnect.dto.ResetPasswordDTO;
import com.br.foodconnect.dto.UpdatePasswordDTO;
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

    @PutMapping("/update")
    public ResponseEntity<CustomerUpdateDTO> updateCustomer(@RequestBody CustomerUpdateDTO dto) throws ParseException {
        return customerService.updateCustomer(dto);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO dto) throws ParseException {
        return customerService.updatePassword(dto);
    }

    @PatchMapping("/resetPassword")
    public ResponseEntity<String> redefinirSenha(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return customerService.resetPassword(resetPasswordDTO);
    }

}
