package com.bootcamp.posapi.service;

import com.bootcamp.posapi.entity.SupplierEntity;
import com.bootcamp.posapi.model.SupplierModel;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<SupplierEntity> getAll();
    Optional<SupplierEntity> getById(Long id);
    Optional<SupplierEntity> save(SupplierModel request);
    Optional<SupplierEntity> update(SupplierModel request, Long id);
    Optional<SupplierEntity> delete(Long id);
}
