package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.Transaction;
import com.kineipe.financemanager.domain.dto.MonthlyBalanceDTO;
import com.kineipe.financemanager.domain.dto.MonthlyBalanceResponseDTO;
import com.kineipe.financemanager.domain.dto.TransactionRequestDTO;
import com.kineipe.financemanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private Logger log = Logger.getLogger(TransactionService.class.getName());

    public Transaction findById(Long id) {
        log.info("Finding transaction by id: " + id);
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        log.info("Found transaction: " + transaction);
        return transaction;
    }

    public Page<Transaction> findAllByUserId(Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("Finding all transactions");
        if (startDate != null && endDate != null) {
            return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate, pageable);
        } else {
            return transactionRepository.findByUserId(userId, pageable);
        }
    }

    public Transaction create(TransactionRequestDTO transactionRequestDTO) {
        log.info("Creating transaction: " + transactionRequestDTO);
        Category category = categoryService.findById(transactionRequestDTO.getCategoryId());
        Account account = accountService.findById(transactionRequestDTO.getAccountId());
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setUserId(transactionRequestDTO.getUserId());
        transaction.setCategory(category);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public Transaction update(TransactionRequestDTO transactionRequestDTO) {
        log.info("Updating transaction: " + transactionRequestDTO);
        Category category = categoryService.findById(transactionRequestDTO.getCategoryId());
        Account account = accountService.findById(transactionRequestDTO.getAccountId());
        Transaction transaction = transactionRepository.findById(transactionRequestDTO.getId()).orElseThrow();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setUserId(transactionRequestDTO.getUserId());
        transaction.setCategory(category);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        log.info("Deleting transaction by id: " + id);
        Transaction entity = transactionRepository.findById(id).orElseThrow();
        transactionRepository.delete(entity);
    }

    public List<MonthlyBalanceResponseDTO> getMonthlyBalances(Long userId) {
        List<MonthlyBalanceDTO> summaries = transactionRepository.getMonthlySummaries(userId);
        List<MonthlyBalanceResponseDTO> responses = new ArrayList<>();

        for (MonthlyBalanceDTO s : summaries) {
            double income = Optional.ofNullable(s.getTotalIncome()).orElse(0.0);
            double expense = Optional.ofNullable(s.getTotalExpense()).orElse(0.0);

            responses.add(new MonthlyBalanceResponseDTO(
                    s.getMonth(),
                    income,
                    expense
            ));
        }

        return responses;
    }
    
}
