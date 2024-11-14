package com.kineipe.financemanager.repository;

import com.kineipe.financemanager.domain.Expectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpectationRepository extends JpaRepository<Expectation, Long> {
}
