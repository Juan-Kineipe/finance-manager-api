package com.kineipe.financemanager.domain.dto;


import java.time.LocalDateTime;

public class TransactionRequestDTO {
    private Long id;
    private Double amount;
    private LocalDateTime date;
    private String description;
    private Long userId;
    private Long categoryId;
    private Long accountId;

    public TransactionRequestDTO() {}

    public TransactionRequestDTO(Long id, Double amount, LocalDateTime date, String description, Long userId, Long categoryId, Long accountId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.categoryId = categoryId;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
