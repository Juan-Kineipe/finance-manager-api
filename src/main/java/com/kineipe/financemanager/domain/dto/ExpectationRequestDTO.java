package com.kineipe.financemanager.domain.dto;


import java.time.LocalDateTime;

public class ExpectationRequestDTO {
    private Long id;
    private Double amount;
    private LocalDateTime date;
    private String description;
    private Long userId;
    private Long categoryId;

    public ExpectationRequestDTO() {}

    public ExpectationRequestDTO(Long id, Double amount, LocalDateTime date, String description, Long userId, Long categoryId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.categoryId = categoryId;
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

}
