package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.dto.AccountRequestDTO;
import com.kineipe.financemanager.domain.enums.AccountTypeEnum;
import com.kineipe.financemanager.repository.AccountRepository;
import com.kineipe.financemanager.service.AccountService;
import com.kineipe.financemanager.service.UserService;
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
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    private Account account;
    private AccountRequestDTO accountRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "user", "password");
        account = new Account(1L, user, "Savings Account", AccountTypeEnum.SAVINGS, 1000.0);

        accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setId(1L);
        accountRequestDTO.setName("Savings Account");
        accountRequestDTO.setType(AccountTypeEnum.SAVINGS);
        accountRequestDTO.setBalance(1000.0);
        accountRequestDTO.setUserId(1L);
    }

    @Test
    void testFindById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.findById(1L);

        assertNotNull(result);
        assertEquals("Savings Account", result.getName());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        Account account2 = new Account(2L, user, "Checking Account", AccountTypeEnum.CHECKING, 2000.0);
        List<Account> list = new ArrayList<>();
        list.add(account);
        list.add(account2);

        when(accountRepository.findAll()).thenReturn(list);

        List<Account> result = accountService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(userService.findById(1L)).thenReturn(user);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.create(accountRequestDTO);

        assertNotNull(result);
        assertEquals("Savings Account", result.getName());
        assertEquals(1000.0, result.getBalance());
        verify(userService, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testUpdate() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.update(accountRequestDTO);

        assertNotNull(result);
        assertEquals("Savings Account", result.getName());
        assertEquals(1000.0, result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testDelete() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        doNothing().when(accountRepository).delete(account);

        accountService.delete(1L);

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).delete(account);
    }
}