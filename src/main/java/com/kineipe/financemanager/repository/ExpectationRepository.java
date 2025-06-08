package com.kineipe.financemanager.repository;

import com.kineipe.financemanager.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectationRepository extends JpaRepository<Expectation, Long> {
    List<Expectation> findAllByUserId(Long userId);
}
