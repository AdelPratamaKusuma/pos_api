package com.bootcamp.posapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order")
public class OrderEntity {
    @Id
    @TableGenerator(name = "tbl_order_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "category_id",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tbl_order_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "invoice_no", length = 32)
    private String invoiceNo;
    @Column(name = "grand_total")
    private Double grandTotal;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "shipped_date")
    private Date shippedDate;
    @Column(name = "ship_name", length = 120)
    private String shipName;
    @Column(name = "ship_address", length = 120)
    private String shipAddres;
    @Column(name = "ship_city", length = 120)
    private String shipCity;
    @Column(name = "ship_region", length = 120)
    private String shipRegion;
    @Column(name = "ship_post_code", length = 120)
    private String shipPostalCode;
    @Column(name = "ship_country", length = 120)
    private String shipCountry;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerEntity customer;

    public void addDetail(OrderDetailEntity item){
        this.orderDetails.add(item);
        item.setOrder(this);
    }
    public void removeDetail(OrderDetailEntity item){
        this.orderDetails.remove(item);
        item.setOrder(null);
    }
}
