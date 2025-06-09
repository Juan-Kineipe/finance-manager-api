package com.kineipe.financemanager.domain.dto;

public record MonthlyBalanceResponseDTO(
        String month,
        double income,
        double expense
) {}
