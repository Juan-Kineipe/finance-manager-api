package com.kineipe.financemanager.controller;


import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/findAll", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PostMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Category> delete(@PathVariable(value = "id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
