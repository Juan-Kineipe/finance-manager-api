package com.kineipe.financemanager.repository;

import com.kineipe.financemanager.domain.Transaction;
import com.kineipe.financemanager.domain.dto.MonthlyBalanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    @Query(value = """
        SELECT 
            DATE_FORMAT(t.date, '%Y-%m') AS month,
            SUM(CASE WHEN c.type = 'INCOME' THEN t.amount ELSE 0 END) AS totalIncome,
            SUM(CASE WHEN c.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS totalExpense
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        WHERE t.user_id = :userId
          AND t.date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<MonthlyBalanceDTO> getMonthlySummaries(@Param("userId") Long userId);
}
