package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.Transaction;
import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.dto.TransactionRequestDTO;
import com.kineipe.financemanager.domain.enums.AccountTypeEnum;
import com.kineipe.financemanager.domain.enums.CategoryTypeEnum;
import com.kineipe.financemanager.repository.TransactionRepository;
import com.kineipe.financemanager.service.AccountService;
import com.kineipe.financemanager.service.CategoryService;
import com.kineipe.financemanager.service.TransactionService;
import com.kineipe.financemanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AccountService accountService;

    private Transaction transaction;
    private TransactionRequestDTO transactionRequestDTO;
    private User user;
    private Category category;
    private Account account;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(1L, user, category, account, 50.0, LocalDate.of(2024, Month.NOVEMBER, 14).atStartOfDay(), "Testing transactions");
        user = new User(1L, "user", "password");
        category = new Category(1L, "Restaurants", CategoryTypeEnum.EXPENSE);
        account = new Account(1L, user, "Savings Account", AccountTypeEnum.SAVINGS, 1000.0);

        transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setId(1L);
        transactionRequestDTO.setAmount(50.0);
        transactionRequestDTO.setDate(LocalDate.of(2024, Month.NOVEMBER, 14).atStartOfDay());
        transactionRequestDTO.setDescription("Testing transactions");
        transactionRequestDTO.setUserId(1L);
        transactionRequestDTO.setCategoryId(1L);
        transactionRequestDTO.setAccountId(1L);
    }

    @Test
    void testFindById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.findById(1L);

        assertNotNull(result);
        assertEquals(50.0, result.getAmount());
        assertEquals(LocalDate.of(2024, Month.NOVEMBER, 14).atStartOfDay(), result.getDate());
        assertEquals("Testing transactions", result.getDescription());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        Transaction transaction2 = new Transaction(2L, user, category, account, 60.0, LocalDate.of(2024, Month.NOVEMBER, 11).atStartOfDay(), "Testing another transaction");
        List<Transaction> list = new ArrayList<>();
        list.add(transaction);
        list.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(list);

        List<Transaction> result = transactionService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(userService.findById(1L)).thenReturn(user);
        when(categoryService.findById(1L)).thenReturn(category);
        when(accountService.findById(1L)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.create(transactionRequestDTO);

        assertNotNull(result);
        assertEquals(50.0, result.getAmount());
        assertEquals(LocalDate.of(2024, Month.NOVEMBER, 14).atStartOfDay(), result.getDate());
        assertEquals("Testing transactions", result.getDescription());
        verify(userService, times(1)).findById(1L);
        verify(categoryService, times(1)).findById(1L);
        verify(accountService, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testUpdate() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.update(transactionRequestDTO);

        assertNotNull(result);
        assertEquals(50.0, result.getAmount());
        assertEquals(LocalDate.of(2024, Month.NOVEMBER, 14).atStartOfDay(), result.getDate());
        assertEquals("Testing transactions", result.getDescription());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testDelete() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(transaction);

        transactionService.delete(1L);

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);
    }
}