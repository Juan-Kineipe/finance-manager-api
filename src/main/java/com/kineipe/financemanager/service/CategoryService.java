package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private Logger log = Logger.getLogger(CategoryService.class.getName());

    public Category findById(Long id) {
        log.info("Finding category by id: " + id);
        Category category = categoryRepository.findById(id).orElseThrow();
        log.info("Found category: " + category);
        return category;
    }

    public List<Category> findAll() {
        log.info("Finding all categories");
        return categoryRepository.findAll();
    }

    public Category create(Category category) {
        log.info("Creating category: " + category);
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        log.info("Updating category: " + category);
        Category entity = categoryRepository.findById(category.getId()).orElseThrow();
        entity.setName(category.getName());
        entity.setType(category.getType());
        return categoryRepository.save(entity);
    }

    public void delete(Long id) {
        log.info("Deleting category by id: " + id);
        Category entity = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(entity);
    }
    
}
