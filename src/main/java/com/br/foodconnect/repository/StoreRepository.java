package com.br.foodconnect.repository;

import com.br.foodconnect.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreModel, Long> {
}
