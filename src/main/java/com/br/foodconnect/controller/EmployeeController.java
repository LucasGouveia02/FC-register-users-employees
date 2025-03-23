package com.br.foodconnect.controller;

import com.br.foodconnect.dto.EmployeeRegisterDTO;
import com.br.foodconnect.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeRegisterDTO> registerEmployee(@RequestBody EmployeeRegisterDTO dto) {
        return employeeService.registerEmployee(dto);
    }

}
