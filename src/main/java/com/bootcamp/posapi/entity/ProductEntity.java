package com.bootcamp.posapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tbl_product")
public class ProductEntity {
    @Id
    @TableGenerator(name = "tbl_product_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "product_id",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tbl_product_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "product_code", length = 16, unique = true)
    private String code;
    @Column(name = "product_name", length = 64)
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "stock")
    private Double stock;
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "unit_on_order")
    private Double unitOnOrder;
    @Column(name = "discontinued")
    private Boolean discontinued;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

   public ProductEntity(String code, String name, Double price, Double stock, Long categoryId){
       this.code = code;
       this.name = name;
       this.price = price;
       this.stock = stock;
       this.categoryId = categoryId;
       this.unitOnOrder = 0.0;
       this.discontinued = false;
   }



}
