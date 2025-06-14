package com.kineipe.financemanager.service;

import com.kineipe.financemanager.domain.Category;
import com.kineipe.financemanager.domain.Expectation;
import com.kineipe.financemanager.domain.dto.ExpectationRequestDTO;
import com.kineipe.financemanager.repository.ExpectationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ExpectationService {

    @Autowired
    ExpectationRepository expectationRepository;

    @Autowired
    CategoryService categoryService;


    public ExpectationService(ExpectationRepository expectationRepository) {
        this.expectationRepository = expectationRepository;
    }

    private Logger log = Logger.getLogger(ExpectationService.class.getName());

    public Expectation findById(Long id) {
        log.info("Finding expectation by id: " + id);
        Expectation expectation = expectationRepository.findById(id).orElseThrow();
        log.info("Found expectation: " + expectation);
        return expectation;
    }

    public List<Expectation> findAllByUserId(Long userId) {
        log.info("Finding all expectations");
        return expectationRepository.findAllByUserId(userId);
    }

    public Expectation create(ExpectationRequestDTO expectationRequestDTO) {
        log.info("Creating expectation: " + expectationRequestDTO);
        Category category = categoryService.findById(expectationRequestDTO.getCategoryId());
        Expectation expectation = new Expectation();
        expectation.setAmount(expectationRequestDTO.getAmount());
        expectation.setUserId(expectationRequestDTO.getUserId());
        expectation.setCategory(category);
        return expectationRepository.save(expectation);
    }

    public Expectation update(ExpectationRequestDTO expectationRequestDTO) {
        log.info("Updating expectation: " + expectationRequestDTO);
        Category category = categoryService.findById(expectationRequestDTO.getCategoryId());
        Expectation expectation = expectationRepository.findById(expectationRequestDTO.getId()).orElseThrow();
        expectation.setAmount(expectationRequestDTO.getAmount());
        expectation.setUserId(expectationRequestDTO.getUserId());
        expectation.setCategory(category);
        return expectationRepository.save(expectation);
    }

    public void delete(Long id) {
        log.info("Deleting expectation by id: " + id);
        Expectation entity = expectationRepository.findById(id).orElseThrow();
        expectationRepository.delete(entity);
    }
    
}
