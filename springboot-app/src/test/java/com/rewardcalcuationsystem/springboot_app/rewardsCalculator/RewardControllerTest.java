package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMonthlyRewards() throws Exception {
        Mockito.when(rewardService.getMonthlyRewards(1L)).thenReturn(Map.of("JANUARY", 115));

        mockMvc.perform(get("/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.JANUARY").value(115));
    }

    @Test
    public void testGetTotalRewards() throws Exception {
        Mockito.when(rewardService.getTotalRewards(1L)).thenReturn(115);

        mockMvc.perform(get("/rewards/1/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(115));
    }

    @Test
    public void testGetMonthlyRewards_CustomerNotFoundException() throws Exception {
        Mockito.when(rewardService.getMonthlyRewards(1L)).thenThrow(new CustomerNotFoundException("No transactions found for customer ID: 1"));

        mockMvc.perform(get("/rewards/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No transactions found for customer ID: 1"));
    }

    @Test
    public void testGetTotalRewards_CustomerNotFoundException() throws Exception {
        Mockito.when(rewardService.getTotalRewards(1L)).thenThrow(new CustomerNotFoundException("No transactions found for customer ID: 1"));

        mockMvc.perform(get("/rewards/1/total"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No transactions found for customer ID: 1"));
    }
}
