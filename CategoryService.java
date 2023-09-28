package com.bootcamp.posapi.service;

import com.bootcamp.posapi.entity.CategoryEntity;
import com.bootcamp.posapi.model.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryEntity> getAll();
    Optional<CategoryEntity> getById(Long id);
    Optional<CategoryEntity> getByCode(String code);
    Optional<CategoryEntity> save(CategoryModel request);
    Optional<CategoryEntity> update(CategoryModel request, Long id);
    Optional<CategoryEntity> delete(Long id);
}
