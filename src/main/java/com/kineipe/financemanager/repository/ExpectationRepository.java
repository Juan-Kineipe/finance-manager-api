package com.kineipe.financemanager.repository;

import com.kineipe.financemanager.domain.Expectation;
import com.kineipe.financemanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectationRepository extends JpaRepository<Expectation, Long> {
    List<Expectation> findAllByUser(User user);
}
