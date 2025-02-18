package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RewardService is a service class that contains the business logic for calculating reward points.
 */
@Service
public class RewardService {

    @Autowired
    private TransactionRepository transactionRepository;

    Logger logger
            = Logger.getLogger(
            RewardService.class.getName());
    /**
     * Calculates the reward points for a given transaction amount.
     *
     * @param amount the transaction amount
     * @return the calculated reward points
     */
    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

    /**
     * Retrieves the monthly reward points for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return a map of month names to reward points
     */
    public Map<String, Integer> getMonthlyRewards(Long customerId) {
        Map<String, Integer> monthlyRewards = new LinkedHashMap<>();
        LocalDate now = LocalDate.now();
        logger.log(Level.INFO, "now {0}", now);

        for (Month month : Month.values()) {
            if (month.getValue() > now.getMonthValue()) {
                break;
            }
            LocalDate startDate = LocalDate.of(now.getYear(), month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

            int monthlyPoints = transactions.stream()
                    .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                    .sum();

            monthlyRewards.put(month.name(), monthlyPoints);
        }
        return monthlyRewards;
        }

    /**
     * Retrieves the total reward points for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return the total reward points
     */
    public int getTotalRewards(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);

        if (transactions.isEmpty()) {
            throw new CustomerNotFoundException("No transactions found for customer ID: " + customerId);
        }

        return transactions.stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                .sum();
    }
}