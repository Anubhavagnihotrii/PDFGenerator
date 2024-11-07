package com.example.pdfGenerator.service;

import com.example.pdfGenerator.dto.InvoiceRequest;
import com.example.pdfGenerator.dto.Item;

import java.math.BigDecimal;

public class InvoiceCalculator {

    public static BigDecimal calculateTotalAmount(InvoiceRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Item item : request.getItems()) {
            BigDecimal rate = item.getRate() != null ? item.getRate() : BigDecimal.ZERO;
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal itemTotal = rate.multiply(quantity);


            item.setAmount(itemTotal);

            totalAmount = totalAmount.add(itemTotal);
        }

        return totalAmount;
    }
}
