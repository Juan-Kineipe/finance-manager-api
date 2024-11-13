package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.Account;
import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.domain.enums.CategoryTypeEnum;
import com.kineipe.financemanager.repository.CategoryRepository;
import com.kineipe.financemanager.service.CategoryService;
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
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Restaurants", CategoryTypeEnum.EXPENSE);
    }

    @Test
    void findAll() {
        Category category2 = new Category(2L, "Payments", CategoryTypeEnum.INCOME);
        List<Category> list = new ArrayList<>();
        list.add(category);
        list.add(category2);

        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void create() {
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.create(category);

        assertNotNull(result);
        assertEquals("Restaurants", result.getName());
        assertEquals(CategoryTypeEnum.EXPENSE, result.getType());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void update() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.update(category);

        assertNotNull(result);
        assertEquals("Restaurants", result.getName());
        assertEquals(CategoryTypeEnum.EXPENSE, result.getType());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void delete() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        categoryService.delete(category.getId());

        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).delete(category);
    }
}