package com.bootcamp.posapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierModel {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String homepage;
}
