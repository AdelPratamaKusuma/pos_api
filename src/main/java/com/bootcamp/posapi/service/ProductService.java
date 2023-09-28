package com.bootcamp.posapi.service;

import com.bootcamp.posapi.entity.ProductEntity;
import com.bootcamp.posapi.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductEntity> getAll();
    Optional<ProductEntity> getById(Long id);
    Optional<ProductEntity> getByCode(String code);
    Optional<ProductEntity> save(ProductModel request);
    Optional<ProductEntity> update(ProductModel request, Long id);
    Optional<ProductEntity> delete(Long id);
}
