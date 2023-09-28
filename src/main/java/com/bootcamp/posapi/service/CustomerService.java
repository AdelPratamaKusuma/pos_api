package com.bootcamp.posapi.service;

import com.bootcamp.posapi.entity.CustomerEntity;
import com.bootcamp.posapi.model.CustomerModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerEntity> getAll();
    Optional<CustomerEntity> getById(Long id);
    Optional<CustomerEntity> save(CustomerModel request);
    Optional<CustomerEntity> update(CustomerModel request, Long id);
    Optional<CustomerEntity> delete(Long id);
}
