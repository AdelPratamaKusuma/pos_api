package com.bootcamp.posapi.repository;

import com.bootcamp.posapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);

    @Query("select t from ProductEntity t where t.id in :ids")
    List<ProductEntity> findByIds(@Param("ids")List<Long> ids);

    List<ProductEntity> findByIdIn(List<Long> ids);

}
