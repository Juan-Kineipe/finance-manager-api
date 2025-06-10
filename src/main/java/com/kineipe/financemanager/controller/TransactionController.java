package com.kineipe.financemanager.controller;


import com.kineipe.financemanager.domain.Transaction;
import com.kineipe.financemanager.domain.dto.MonthlyBalanceResponseDTO;
import com.kineipe.financemanager.domain.dto.TransactionRequestDTO;
import com.kineipe.financemanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transaction findById(@PathVariable(value = "id") Long id) {
        return transactionService.findById(id);
    }

    @GetMapping(value = "/findAll", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Page<Transaction>> findAll(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "50") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {

        Long userId = (Long) authentication.getPrincipal();
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "date"));
        return ResponseEntity.ok(transactionService.findAllByUserId(userId, startDate, endDate, pageable));
    }

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transaction create(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionService.create(transactionRequestDTO);
    }

    @PostMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transaction update(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionService.update(transactionRequestDTO);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Transaction> delete(@PathVariable(value = "id") Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/monthlyBalance")
    public List<MonthlyBalanceResponseDTO> getMonthlyBalance(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return transactionService.getMonthlyBalances(userId);
    }

}
