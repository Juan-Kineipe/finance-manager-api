package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.Expectation;
import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.dto.ExpectationRequestDTO;
import com.kineipe.financemanager.domain.enums.CategoryTypeEnum;
import com.kineipe.financemanager.repository.ExpectationRepository;
import com.kineipe.financemanager.service.AccountService;
import com.kineipe.financemanager.service.CategoryService;
import com.kineipe.financemanager.service.ExpectationService;
import com.kineipe.financemanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ExpectationServiceTest {

    @InjectMocks
    private ExpectationService expectationService;

    @Mock
    private ExpectationRepository expectationRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AccountService accountService;

    private Expectation expectation;
    private ExpectationRequestDTO expectationRequestDTO;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        expectation = new Expectation(1L, user, category, 500.0);
        user = new User(1L, "user", "password");
        category = new Category(1L, "Restaurants", CategoryTypeEnum.EXPENSE);

        expectationRequestDTO = new ExpectationRequestDTO();
        expectationRequestDTO.setId(1L);
        expectationRequestDTO.setAmount(500.0);
        expectationRequestDTO.setUserId(1L);
        expectationRequestDTO.setCategoryId(1L);
    }

    @Test
    void testFindById() {
        when(expectationRepository.findById(1L)).thenReturn(Optional.of(expectation));

        Expectation result = expectationService.findById(1L);

        assertNotNull(result);
        assertEquals(500.0, result.getAmount());
        verify(expectationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        Category category2 = new Category(2L, "Bars", CategoryTypeEnum.EXPENSE);
        Expectation expectation2 = new Expectation(2L, user, category2, 700.0);
        List<Expectation> list = new ArrayList<>();
        list.add(expectation);
        list.add(expectation2);

        when(expectationRepository.findAll()).thenReturn(list);

        List<Expectation> result = expectationService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(expectationRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(userService.findById(1L)).thenReturn(user);
        when(categoryService.findById(1L)).thenReturn(category);
        when(expectationRepository.save(any(Expectation.class))).thenReturn(expectation);

        Expectation result = expectationService.create(expectationRequestDTO);

        assertNotNull(result);
        assertEquals(500.0, result.getAmount());
        verify(userService, times(1)).findById(1L);
        verify(categoryService, times(1)).findById(1L);
        verify(expectationRepository, times(1)).save(any(Expectation.class));
    }

    @Test
    void testUpdate() {
        when(expectationRepository.findById(1L)).thenReturn(Optional.of(expectation));
        when(expectationRepository.save(any(Expectation.class))).thenReturn(expectation);

        Expectation result = expectationService.update(expectationRequestDTO);

        assertNotNull(result);
        assertEquals(500.0, result.getAmount());
        verify(expectationRepository, times(1)).findById(1L);
        verify(expectationRepository, times(1)).save(expectation);
    }

    @Test
    void testDelete() {
        when(expectationRepository.findById(1L)).thenReturn(Optional.of(expectation));
        doNothing().when(expectationRepository).delete(expectation);

        expectationService.delete(1L);

        verify(expectationRepository, times(1)).findById(1L);
        verify(expectationRepository, times(1)).delete(expectation);
    }
}