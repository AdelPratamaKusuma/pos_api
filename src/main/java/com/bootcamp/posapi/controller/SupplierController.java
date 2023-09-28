package com.bootcamp.posapi.controller;

import com.bootcamp.posapi.entity.SupplierEntity;
import com.bootcamp.posapi.model.ResponseModel;
import com.bootcamp.posapi.model.SupplierModel;
import com.bootcamp.posapi.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @GetMapping
    private ResponseEntity<ResponseModel> getAll(){
        List<SupplierEntity> data = this.service.getAll();
        return ResponseEntity.ok()
                .body(new ResponseModel(200,"SUCCESS", data));
    }

    @GetMapping("/{id}")
    private ResponseEntity<ResponseModel> getById(@PathVariable("id") Long id){
        Optional<SupplierEntity> data = this.service.getById(id);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseModel(400,"FAILED","Data not found"));
    }

    @PostMapping()
    private ResponseEntity<ResponseModel> save(@RequestBody SupplierModel request){
        Optional<SupplierEntity> data = this.service.save(request);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500,"FAILED","Save Supplier failed"));
    }

    @PatchMapping("/{id}")
    private ResponseEntity<ResponseModel> update(@RequestBody SupplierModel request, @PathVariable("id") Long id){
        Optional<SupplierEntity> data = this.service.update(request, id);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500,"FAILED","Update Supplier failed"));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseModel> delete(@PathVariable("id") Long id){
        Optional<SupplierEntity> data = this.service.delete(id);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500,"FAILED","Delete Supplier failed"));
    }



}
