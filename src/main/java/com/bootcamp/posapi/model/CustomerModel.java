package com.bootcamp.posapi.model;

import com.bootcamp.posapi.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public CustomerModel(CustomerEntity entity){
        BeanUtils.copyProperties(entity, this);
    }
}
