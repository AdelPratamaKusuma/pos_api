package com.bootcamp.posapi.repository;

import com.bootcamp.posapi.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<TokenEntity, Long> {
    @Query("select t from TokenEntity t join UserEntity u on t.userId=u.id " +
            "where u.id = :userId and (t.expired = false or t.revoked = false)")
    List<TokenEntity> findAllValidTokenByUser(@Param("userId") Long userId);

    Optional<TokenEntity> findByToken(String token);

}
