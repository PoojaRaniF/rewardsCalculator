package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculatePoints() {
        assertEquals(90, rewardService.calculatePoints(120.0));
        assertEquals(25, rewardService.calculatePoints(75.0));
        assertEquals(0, rewardService.calculatePoints(50.0));
    }

    @Test
    public void testGetMonthlyRewards() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 120.0, LocalDate.of(2025, 1, 15)),
                new Transaction(1L, 75.0, LocalDate.of(2025, 1, 20))
        );
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(1L, LocalDate.of(2025, Month.JANUARY, 1), LocalDate.of(2025, Month.JANUARY, 31)))
                .thenReturn(transactions);

        Map<String, Integer> monthlyRewards = rewardService.getMonthlyRewards(1L);
        assertEquals(115, monthlyRewards.get("JANUARY"));
    }

    @Test
    public void testGetTotalRewards() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 120.0, LocalDate.of(2025, 1, 15)),
                new Transaction(1L, 75.0, LocalDate.of(2025, 1, 20))
        );
        when(transactionRepository.findByCustomerId(1L)).thenReturn(transactions);

        int totalRewards = rewardService.getTotalRewards(1L);
        assertEquals(115, totalRewards);
    }

    @Test
    public void testGetMonthlyRewards_CustomerNotFoundException() {
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31)))
                .thenReturn(Collections.emptyList());

        assertThrows(CustomerNotFoundException.class, () -> {
            rewardService.getMonthlyRewards(1L);
        });
    }

    @Test
    public void testGetTotalRewards_CustomerNotFoundException() {
        when(transactionRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());

        assertThrows(CustomerNotFoundException.class, () -> {
            rewardService.getTotalRewards(1L);
        });
    }
}