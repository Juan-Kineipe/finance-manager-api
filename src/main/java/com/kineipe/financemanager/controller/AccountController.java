package com.kineipe.financemanager.controller;


import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/findById/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Account findById(@PathVariable(value = "id") Long id) {
        return accountService.findById(id);
    }

    @GetMapping(value = "/findAll", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PostMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Account update(@RequestBody Account account) {
        return accountService.update(account);
    }

    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Account> delete(@PathVariable(value = "id") Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
