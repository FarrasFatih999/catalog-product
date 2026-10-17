package com.budiluhur.catalog.service;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public double calculateDiscountedPrice(double originalPrice, double discountPercentage) {
        return originalPrice - (originalPrice * discountPercentage / 100);
    }
}