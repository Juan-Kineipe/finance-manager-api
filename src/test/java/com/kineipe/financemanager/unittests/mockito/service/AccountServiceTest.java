package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.repository.AccountRepository;
import com.kineipe.financemanager.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService service;

    @Mock
    private AccountRepository repository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L, "user", "password");
    }

    @Test
    void findById() {
        when(repository.findById(account.getId())).thenReturn(Optional.of(account));

        Account result = service.findById(1L);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(repository, times(1)).findById(account.getId());
    }

    @Test
    void findAll() {
        Account account2 = new Account(2L, "user2", "password2");
        Account account3 = new Account(3L, "user3", "password3");
        List<Account> list = new ArrayList<Account>();
        list.add(account);
        list.add(account2);
        list.add(account3);

        when(repository.findAll()).thenReturn(list);

        List<Account> result = service.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create() {
        when(repository.save(account)).thenReturn(account);

        Account result = service.create(account);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(repository, times(1)).save(account);
    }

    @Test
    void update() {
        Account updatedAccount = new Account(1L, "updatedUser", "updatedPassword");

        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        when(repository.save(updatedAccount)).thenReturn(updatedAccount);

        Account result = service.update(updatedAccount);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        assertEquals("updatedPassword", result.getPassword());
        verify(repository, times(1)).save(updatedAccount);
    }

    @Test
    void delete() {
        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        doNothing().when(repository).delete(account);

        service.delete(account.getId());

        verify(repository, times(1)).findById(account.getId());
        verify(repository, times(1)).delete(account);
    }
}