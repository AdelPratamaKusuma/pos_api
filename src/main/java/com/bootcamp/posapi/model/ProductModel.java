package com.bootcamp.posapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private Double stock;
    private Long categoryId;
}
