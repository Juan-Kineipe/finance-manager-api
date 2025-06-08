package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.dto.AccountRequestDTO;
import com.kineipe.financemanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private Logger log = Logger.getLogger(AccountService.class.getName());

    public Account findById(Long id) {
        log.info("Finding account by id: " + id);
        Account account = accountRepository.findById(id).orElseThrow();
        log.info("Found account: " + account);
        return account;
    }

    public List<Account> findAllByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account create(AccountRequestDTO accountRequestDTO) {
        log.info("Creating account: " + accountRequestDTO);
        Account account = new Account();
        account.setName(accountRequestDTO.getName());
        account.setType(accountRequestDTO.getType());
        account.setBalance(accountRequestDTO.getBalance());
        account.setUserId(accountRequestDTO.getUserId());
        return accountRepository.save(account);
    }

    public Account update(AccountRequestDTO accountRequestDTO) {
        log.info("Updating account: " + accountRequestDTO);
        Account account = accountRepository.findById(accountRequestDTO.getId()).orElseThrow();
        account.setName(accountRequestDTO.getName());
        account.setType(accountRequestDTO.getType());
        account.setBalance(accountRequestDTO.getBalance());
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        log.info("Deleting account by id: " + id);
        Account entity = accountRepository.findById(id).orElseThrow();
        accountRepository.delete(entity);
    }
    
}
