package com.bootcamp.posapi.controller;

import com.bootcamp.posapi.entity.CategoryEntity;
import com.bootcamp.posapi.model.CategoryModel;
import com.bootcamp.posapi.model.ResponseModel;
import com.bootcamp.posapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CatgeoryController {
    private final CategoryService service;

    @GetMapping
    private ResponseEntity<ResponseModel> getAll(){
        List<CategoryEntity> data = this.service.getAll();
        return ResponseEntity.ok()
                .body(new ResponseModel(200, "SUCCESS", data));
    }
    @GetMapping("/{id}")
    private ResponseEntity<ResponseModel> getById(@PathVariable("id") Long id){
        Optional<CategoryEntity> data = this.service.getById(id);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseModel(400,"FAILED","Data not found"));
    }

    @PostMapping()
    private ResponseEntity<ResponseModel> save(@RequestBody CategoryModel request){
        Optional<CategoryEntity> data = this.service.save(request);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500, "FAILED", "Save category failed"));
    }
    @PatchMapping("/{id}")
    private ResponseEntity<ResponseModel> update(@RequestBody CategoryModel request, @PathVariable("id") Long id){
        Optional<CategoryEntity> data = this.service.update(request, id);
        if(data.isPresent()) {
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500,"FAILED","Update category failed"));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseModel> delete(@PathVariable("id") Long id){
        Optional<CategoryEntity> data = this.service.delete(id);
        if(data.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", data));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel(500, "FAILED", "Delete category failed"));
    }



}
