package com.br.foodconnect.controller;

import com.br.foodconnect.dto.EmployeeAlterDTO;
import com.br.foodconnect.dto.EmployeeDTO;
import com.br.foodconnect.dto.EmployeeRegisterDTO;
import com.br.foodconnect.model.EmployeeModel;
import com.br.foodconnect.service.EmployeeService;
import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRegisterDTO dto) {
        return employeeService.registerEmployee(dto);
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<EmployeeModel> alterEmployee(@PathVariable Long id, @RequestBody EmployeeAlterDTO dto) throws ParseException, java.text.ParseException {
        return employeeService.alterEmployee(id, dto);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}

