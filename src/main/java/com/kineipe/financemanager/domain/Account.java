package com.kineipe.financemanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kineipe.financemanager.domain.enums.AccountTypeEnum;
import jakarta.persistence.*;


@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;
    @Column
    private Double balance;

    public Account() {}

    public Account(Long id, Long userId, String name, AccountTypeEnum type, Double balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountTypeEnum getType() {
        return type;
    }

    public void setType(AccountTypeEnum type) {
        this.type = type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
