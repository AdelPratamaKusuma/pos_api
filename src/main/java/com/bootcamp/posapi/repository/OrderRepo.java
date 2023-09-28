package com.bootcamp.posapi.repository;

import com.bootcamp.posapi.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByInvoiceNo(String invoiceNo);

    List<OrderEntity> findByCustomerId(Long customerId);
}
