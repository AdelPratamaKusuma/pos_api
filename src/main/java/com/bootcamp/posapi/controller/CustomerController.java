package com.bootcamp.posapi.controller;

import com.bootcamp.posapi.entity.CustomerEntity;
import com.bootcamp.posapi.model.CustomerModel;
import com.bootcamp.posapi.model.ResponseModel;
import com.bootcamp.posapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    private ResponseEntity<ResponseModel> getAll(){
        List<CustomerEntity> data = this.service.getAll();
        return ResponseEntity.ok()
                .body(new ResponseModel(200,"SUCCESS", data));
    }
    @GetMapping("/{id}")
    private ResponseEntity<ResponseModel>getById(@PathVariable("id")Long id){
        Optional<CustomerEntity> data = this.service.getById(id);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseModel(400, "FAILED", "Data not found"));
    }

    @PostMapping()
    private ResponseEntity<ResponseModel> save(@RequestBody CustomerModel request){
        Optional<CustomerEntity> data = this.service.save(request);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500, "FAILED", "Save customer failed"));

    }

    @PatchMapping("/{id}")
    private ResponseEntity<ResponseModel> update(@RequestBody CustomerModel request, @PathVariable("id") Long id){
        Optional<CustomerEntity> data = this.service.update(request, id);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500, "FAILED", "update customer failed"));

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseModel> delete(@PathVariable("id")Long id){
        Optional<CustomerEntity> data = this.service.delete(id);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500, "FAILED", "Delete customer failed"));
    }
}
