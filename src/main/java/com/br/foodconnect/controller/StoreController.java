package com.br.foodconnect.controller;

import com.br.foodconnect.dto.request.StoreRegisterDTO;
import com.br.foodconnect.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody StoreRegisterDTO dto) throws ParseException {
        return storeService.registerStore(dto);
    }

    @PatchMapping("/openClose")
    public ResponseEntity<?> openCloseStore(@RequestParam Boolean open,
                                       @RequestParam Long storeId) throws ParseException {
        return storeService.openCloseStore(open, storeId);
    }
    @GetMapping("/status")
    public ResponseEntity<Boolean> getStoreStatus(@RequestParam Long storeId) {
        boolean isOpen = storeService.getStoreStatus(storeId);
        return ResponseEntity.ok(isOpen);
    }

}
