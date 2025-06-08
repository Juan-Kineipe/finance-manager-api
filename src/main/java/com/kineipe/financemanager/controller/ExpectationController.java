package com.kineipe.financemanager.controller;


import com.kineipe.financemanager.domain.Expectation;
import com.kineipe.financemanager.domain.dto.ExpectationRequestDTO;
import com.kineipe.financemanager.service.ExpectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expectation")
public class ExpectationController {

    @Autowired
    ExpectationService expectationService;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Expectation findById(@PathVariable(value = "id") Long id) {
        return expectationService.findById(id);
    }

    @GetMapping(value = "/findAll", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Expectation> findAll(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return expectationService.findAllByUserId(userId);
    }

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Expectation create(@RequestBody ExpectationRequestDTO expectationRequestDTO) {
        return expectationService.create(expectationRequestDTO);
    }

    @PostMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Expectation update(@RequestBody ExpectationRequestDTO expectationRequestDTO) {
        return expectationService.update(expectationRequestDTO);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Expectation> delete(@PathVariable(value = "id") Long id) {
        expectationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
