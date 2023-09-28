package com.bootcamp.posapi.service.impl;

import com.bootcamp.posapi.entity.CustomerEntity;
import com.bootcamp.posapi.entity.OrderDetailEntity;
import com.bootcamp.posapi.entity.OrderEntity;
import com.bootcamp.posapi.entity.ProductEntity;
import com.bootcamp.posapi.model.CustomerModel;
import com.bootcamp.posapi.model.OrderDetailModel;
import com.bootcamp.posapi.model.OrderModel;
import com.bootcamp.posapi.repository.CustomerRepo;
import com.bootcamp.posapi.repository.OrderRepo;
import com.bootcamp.posapi.repository.ProductRepo;
import com.bootcamp.posapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;


    @Override
    public List<OrderModel> getAll() {
        List<OrderEntity> result = this.orderRepo.findAll();
        if(result.isEmpty()){
            return Collections.emptyList();
        }
        return result.stream().map(OrderModel::new).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderModel> getById(Long id) {
        if(id == 0L)
        return Optional.empty();
        var result = this.orderRepo.findById(id).orElse(null);
        if(result == null){
            return Optional.empty();
        }
        OrderModel orderModel = new OrderModel(result);
        return Optional.of(orderModel);
    }

    @Override
    public List<OrderModel> getByCustomerId(Long customerId) {
       if(customerId == 0L){
           return Collections.emptyList();
       }
       var result = this.orderRepo.findByCustomerId(customerId);
       return result.stream().map(OrderModel::new)
               .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderModel> getByInvoiceNo(String invoiceNo) {
        if(invoiceNo == null || invoiceNo.isEmpty())
            return Optional.empty();
        var result = this.orderRepo.findByInvoiceNo(invoiceNo).orElse(null);
        if(result == null){
            return Optional.empty();
        }
        OrderModel orderModel = new OrderModel(result);
        return Optional.of(orderModel);
    }

    @Override
    public Optional<OrderModel> save(OrderModel request) {
        if(request.getCustomer()== null){
            log.error("Save Order failed, customer is null");
            return Optional.empty();
        }

        // check customer
        CustomerEntity customer = this.getCustomer(request.getCustomer());
        if(customer == null) {
            log.error("Save Order failed, customer not found");
            return Optional.empty();
        }
        // check product
        boolean productIsValid = this.isProductValid(request.getOrderDetail());
        if(!productIsValid){
            log.error("Save Order failed. product not valid");
            return Optional.empty();
        }
        OrderEntity entity = this.newOrder(request, customer.getId());

        try{
            // try to save to the database
            this.orderRepo.saveAndFlush(entity);
            // if success then return option order
            OrderModel response = new OrderModel(entity);

            return Optional.of(response);
        }catch (Exception e){
            // when save is failed, then rise the log massage
            log.error("Save Order failed, error: {}", e.getMessage());
            return Optional.empty();
        }

    }

    private OrderEntity newOrder(OrderModel request, Long customerId) {
        OrderEntity order = new OrderEntity();

        //copy shipper
        BeanUtils.copyProperties(request.getShip(), order);
        // set invoice
        order.setInvoiceNo(request.getInvoiceNo());
        // set customer id
        order.setCustomerId(customerId);
        // set date
        order.setOrderDate(new Date());

        // var grandTotal
        Double grandTotal = 0.0;
                for (OrderDetailModel detail:request.getOrderDetail()){
                    // call constructor OrderDetailEntity with prams
                    OrderDetailEntity detailEntity = new OrderDetailEntity(order, detail.getProductId(), detail.getPrice(), detail.getQuantity());
                    // call method addDetail
                    order.addDetail(detailEntity);
                    // grandTotal plus subtotal
                    grandTotal += detailEntity.getSubTotal();
                }
                // set grand grandTotal
                order.setGrandTotal(grandTotal);
                return order;
    }

    public boolean isProductValid(List<OrderDetailModel> details) {
        List<Long> ids = details.stream().map(OrderDetailModel::getProductId)
                .collect(Collectors.toList());
        // get product dari database
        List<ProductEntity> products = this.productRepo.findByIdIn(ids);
        // jika product di database kosong, return tidak valid
        if(products.isEmpty()){
            return false;
        }
        // jika jumlah product di database tidak sama dengan product yang di kirim
        // return tidal valid
        if(products.size() != details.size()){
            return false;
        }
        // return valid, jika jumlah stock lebih dari 0
        for(ProductEntity product: products){
            var detailModel = details.stream()
                    .filter(detail -> detail.getProductId() == product.getId())
                    .findFirst().orElse(null);
            // jika detail model nya null
            if(detailModel == null){
                return false;
            }
            // jumlah stock kurang dari jumlah yang dibeli
            if(product.getStock() < detailModel.getQuantity()){
                return false;
            }
        }
        return true;
    }

    private CustomerEntity getCustomer(CustomerModel request) {
        // check customer from database
        var result = this.customerRepo.findByEmail(request.getEmail()).orElse(null);
        // when customer is not exist on database then create new customer
        if(result == null){
           //set new object customer
            CustomerEntity customer = new CustomerEntity();
            // copy value property
            BeanUtils.copyProperties(request, customer);
            try{
                // save to the database
                this.customerRepo.saveAndFlush(customer);
                // if success, then return value customer
                return customer;
            }catch (Exception e){
                // when error, set log
                log.error("Save customer failed, error: {}", e.getMessage());
                // return null
                return null;
            }

        }
        // get customer from database is existed
        // then return the result
        return result;

    }


    @Override
    public Optional<OrderEntity> update(OrderModel request, Long id) {
        if(id == 0L)
        return Optional.empty();

        OrderEntity entity = this.orderRepo.findById(id).orElse(null);
        if(entity == null)
            return Optional.empty();

        BeanUtils.copyProperties(request, entity);
        try{
            this.orderRepo.save(entity);
            return Optional.of(entity);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<OrderEntity> delete(Long id) {
        if(id == 0L)
        return Optional.empty();

        OrderEntity entity = this.orderRepo.findById(id).orElse(null);
        if(entity == null)
            return Optional.empty();

        try{
            this.orderRepo.delete(entity);
            return Optional.of(entity);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
