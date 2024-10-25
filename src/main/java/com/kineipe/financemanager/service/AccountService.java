package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    private Logger log = Logger.getLogger(AccountService.class.getName());

    public Account findById(Long id) {
        log.info("Finding account by Id: " + id);
        return accountRepository.findById(id).orElseThrow();
    }

    public List<Account> findAll() {
        log.info("Finding all accounts");
        return accountRepository.findAll();
    }

    public Account create(Account account) {
        log.info("Creating account");
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        log.info("Updating account");
        Account entity = accountRepository.findById(account.getId()).orElseThrow();
        entity.setUsername(account.getUsername());
        entity.setPassword(account.getPassword());
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        log.info("Deleting account");
        Account entity = accountRepository.findById(id).orElseThrow();
        accountRepository.delete(entity);
    }
}
