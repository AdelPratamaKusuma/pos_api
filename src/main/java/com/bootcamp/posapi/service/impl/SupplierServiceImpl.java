package com.bootcamp.posapi.service.impl;

import com.bootcamp.posapi.entity.SupplierEntity;
import com.bootcamp.posapi.model.SupplierModel;
import com.bootcamp.posapi.repository.SupplierRepo;
import com.bootcamp.posapi.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;

    @Override
    public List<SupplierEntity> getAll() {
        return this.supplierRepo.findAll();
    }

    @Override
    public Optional<SupplierEntity> getById(Long id) {
        if(id == 0L)
            return Optional.empty();

        return this.supplierRepo.findById(id);
    }

    @Override
    public Optional<SupplierEntity> save(SupplierModel request) {
        SupplierEntity entity = new SupplierEntity();
        BeanUtils.copyProperties(request, entity);
        try{
            this.supplierRepo.save(entity);
            return Optional.of(entity);
        }catch (Exception e){
            log.error("Save Supplier failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<SupplierEntity> update(SupplierModel request, Long id) {
       if( id == 0L)
           return Optional.empty();

       SupplierEntity entity = this.supplierRepo.findById(id).orElse(null);
       if(entity == null)
           return Optional.empty();

       BeanUtils.copyProperties(request, entity);
       try{
           this.supplierRepo.save(entity);
           return Optional.of(entity);
       }catch (Exception e){
           return Optional.empty();
       }
    }

    @Override
    public Optional<SupplierEntity> delete(Long id) {
        if(id == 0L)
        return Optional.empty();

        SupplierEntity entity = this.supplierRepo.findById(id).orElse(null);
        if(entity == null)
            return Optional.empty();

        try{
            this.supplierRepo.delete(entity);
            return Optional.of(entity);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
