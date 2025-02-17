package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private TransactionRepository transactionRepository;

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

    public Map<String, Integer> getMonthlyRewards(Long customerId) {
        Map<String, Integer> monthlyRewards = new HashMap<>();
        LocalDate now = LocalDate.now();

        for (Month month : Month.values()) {
            LocalDate startDate = LocalDate.of(now.getYear(), month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

            if (transactions.isEmpty()) {
                throw new CustomerNotFoundException("No transactions found for customer ID: " + customerId);
            }

            int monthlyPoints = transactions.stream()
                    .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                    .sum();

            monthlyRewards.put(month.name(), monthlyPoints);
        }

        return monthlyRewards;
    }

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