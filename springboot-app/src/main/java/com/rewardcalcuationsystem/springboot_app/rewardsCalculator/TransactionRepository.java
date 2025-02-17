package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * TransactionRepository is a repository interface for managing Transaction entities.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds transactions for a specific customer within a date range.
     *
     * @param customerId the ID of the customer
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions for the specified customer within the date range
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);

    /**
     * Finds all transactions for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return a list of transactions for the specified customer
     */
    List<Transaction> findByCustomerId(Long customerId);
}