package com.bootcamp.posapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="tbl_category")
public class CategoryEntity {
    @Id
    @TableGenerator(name = "tbl_category_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "category_id",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tbl_catgeory_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "category_code", length = 16, unique = true)
    private String code;
    @Column(name = "category_name", length = 64, unique = true)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

    public CategoryEntity(String code, String name){
        this.code = code;
        this.name = name;
    }

}
