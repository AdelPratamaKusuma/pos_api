package com.bootcamp.posapi.model;

import com.bootcamp.posapi.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipModel {
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
    private Date shippedDate;

    public OrderShipModel(OrderEntity entity){
        BeanUtils.copyProperties(entity, this);
    }
}
