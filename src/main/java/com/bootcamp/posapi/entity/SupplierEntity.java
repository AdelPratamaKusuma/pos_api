package com.bootcamp.posapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_supplier")
public class SupplierEntity {
    @Id
    @TableGenerator(name = "tbl_supplier_seq",
    table = "tbl_sequence",
    pkColumnValue = "squence_id",
    valueColumnName = "sequence_value",
    pkColumnName = "category_id",
    allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "supplier_name",length = 64)
    private String name;
    @Column(name = "supp;ier_email", length = 64)
    private String email;
    @Column(name = "supplier_phone", length = 20)
    private String phone;
    @Column(name = "address", length = 120)
    private String address;
    @Column(name = "homepage", length = 120)
    private String homepage;

    public SupplierEntity(String name, String email, String phone, String address, String homepage) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.homepage = homepage;
    }
}
