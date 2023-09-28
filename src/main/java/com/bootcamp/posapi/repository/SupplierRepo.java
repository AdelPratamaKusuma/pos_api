package com.bootcamp.posapi.repository;

import com.bootcamp.posapi.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<SupplierEntity, Long> {
}
