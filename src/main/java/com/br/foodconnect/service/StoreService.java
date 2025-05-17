package com.br.foodconnect.service;

import com.br.foodconnect.dto.request.StoreRegisterDTO;
import com.br.foodconnect.model.StoreModel;
import com.br.foodconnect.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public ResponseEntity<?> registerStore(StoreRegisterDTO store) {

        if (store.getName() == null || store.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Informe o nome da loja!");
        }
        if (store.getCnpj() == null || store.getCnpj().isEmpty()) {
            return ResponseEntity.badRequest().body("Informe o cnpj da loja!");
        }
        if (store.getFoodCourt() == null || store.getFoodCourt().isEmpty()) {
            return ResponseEntity.badRequest().body("Informe em qual praça de alimentação a loja está localizada. Exemplo: P2");
        }
        StoreModel storeModel = new StoreModel();
        storeModel.setOpen(false);
        storeModel.setCpnj(store.getCnpj());
        storeModel.setEnabled(true);
        storeModel.setName(store.getName());
        storeModel.setFoodCourt(store.getFoodCourt());

        storeRepository.save(storeModel);

        return ResponseEntity.ok("Loja registrada com sucesso!");
    }

    public ResponseEntity<?> openCloseStore(Boolean open, Long storeId) {
        StoreModel storeModel = storeRepository.findById(storeId).orElse(null);
        if (storeModel == null) {
            return ResponseEntity.notFound().build();
        }
        storeModel.setOpen(open);
        storeRepository.save(storeModel);
        if (open) {
            return ResponseEntity.ok("Loja aberta com sucesso!");
        } else {
            return ResponseEntity.ok("Loja fechada com sucesso!");
        }
    }
}
