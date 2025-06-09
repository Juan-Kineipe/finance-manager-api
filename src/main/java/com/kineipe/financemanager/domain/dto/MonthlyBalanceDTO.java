package com.kineipe.financemanager.domain.dto;

public interface MonthlyBalanceDTO {
    String getMonth();
    Double getTotalIncome();
    Double getTotalExpense();
}