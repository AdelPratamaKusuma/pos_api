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
@Table(name = "tbl_order_detail")
public class OrderDetailEntity {
    @Id
    @TableGenerator(name = "tbl_order_detail_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "category_id",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tbl_order_detail_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "unit_price", nullable = false)
    private Double price;

    @Column(name = "quantity",nullable = false)
    private Double quantity;

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    public OrderDetailEntity(OrderEntity order, Long productId, Double price, Double quantity){
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = price * quantity;
    }


}
