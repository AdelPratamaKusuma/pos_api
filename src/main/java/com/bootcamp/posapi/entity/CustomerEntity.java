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
@Table(name = "tbl_customer")
public class CustomerEntity {
    @Id
    @TableGenerator(name = "tbl_customer_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "category_id",
    allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 64, unique = true)
    private String name;
    @Column(name = "customer_email", length = 20, unique = true)
    private String email;
    @Column(name = "customer_phone", length = 20, unique = true)
    private String phone;
    @Column(name = "address", length = 120)
    private String address;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> order = new ArrayList<>();

    public CustomerEntity(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}

