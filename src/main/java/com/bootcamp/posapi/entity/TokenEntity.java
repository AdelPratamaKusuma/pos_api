package com.bootcamp.posapi.entity;

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
@Table(name = "tbl_token")
public class TokenEntity {
    @Id
    @TableGenerator(name = "tbl_token_seq",
    table = "tbl_sequence",
    pkColumnName = "sequence_id",
    valueColumnName = "sequence_value",
    pkColumnValue = "token_id",
    allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true, length = 1028)
    private String token;
    @Column(name = "token_type", length = 16)
    private String tokenType;
    @Column(name = "revoked")
    private boolean revoked;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public UserEntity user;
}
