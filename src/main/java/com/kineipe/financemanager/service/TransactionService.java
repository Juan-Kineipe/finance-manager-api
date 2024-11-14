package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.Transaction;
import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.dto.TransactionRequestDTO;
import com.kineipe.financemanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

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

    public List<Transaction> findAll() {
        log.info("Finding all transactions");
        return transactionRepository.findAll();
    }

    public Transaction create(TransactionRequestDTO transactionRequestDTO) {
        log.info("Creating transaction: " + transactionRequestDTO);
        User user = userService.findById(transactionRequestDTO.getUserId());
        Category category = categoryService.findById(transactionRequestDTO.getCategoryId());
        Account account = accountService.findById(transactionRequestDTO.getAccountId());
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public Transaction update(TransactionRequestDTO transactionRequestDTO) {
        log.info("Updating transaction: " + transactionRequestDTO);
        User user = userService.findById(transactionRequestDTO.getUserId());
        Category category = categoryService.findById(transactionRequestDTO.getCategoryId());
        Account account = accountService.findById(transactionRequestDTO.getAccountId());
        Transaction transaction = transactionRepository.findById(transactionRequestDTO.getId()).orElseThrow();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        log.info("Deleting transaction by id: " + id);
        Transaction entity = transactionRepository.findById(id).orElseThrow();
        transactionRepository.delete(entity);
    }
    
}
