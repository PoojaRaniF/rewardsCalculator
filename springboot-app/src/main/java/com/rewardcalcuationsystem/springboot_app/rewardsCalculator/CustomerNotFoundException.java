package com.rewardcalcuationsystem.springboot_app.rewardsCalculator;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}