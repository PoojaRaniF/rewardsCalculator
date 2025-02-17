package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rewards")
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, Integer>> getMonthlyRewards(@PathVariable Long customerId) {
        Map<String, Integer> rewards = rewardService.getMonthlyRewards(customerId);
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/{customerId}/total")
    public ResponseEntity<Integer> getTotalRewards(@PathVariable Long customerId) {
        int totalRewards = rewardService.getTotalRewards(customerId);
        return ResponseEntity.ok(totalRewards);
    }
}
