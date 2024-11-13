package com.example.pdfGenerator.service;

import com.example.pdfGenerator.dto.InvoiceRequest;
import com.example.pdfGenerator.dto.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceCalculatorTest {
    @Test
    void testCalculateTotalAmount_NoItems(){
        InvoiceRequest request = new InvoiceRequest();
        request.setItems(List.of());
        assertEquals(BigDecimal.ZERO,InvoiceCalculator.calculateTotalAmount(request));
    }

    @Test
    void testCalculateTotalAmount_OneItem(){
        InvoiceRequest request = new InvoiceRequest();
        Item item = new Item();
        item.setRate(new BigDecimal("10.00"));
        item.setQuantity(2);
        request.setItems(List.of(item));
        assertEquals(new BigDecimal("20.00"),InvoiceCalculator.calculateTotalAmount(request));
    }

    @Test
    void testCalculateTotalAmount_MultipleItems(){
        InvoiceRequest request = new InvoiceRequest();
        Item item1 =  new Item();
        item1.setRate(new BigDecimal("10.00"));
        item1.setQuantity(2);
        Item item2 =  new Item();
        item2.setRate(new BigDecimal("20.00"));
        item2.setQuantity(4);
        request.setItems(List.of(item1,item2));
        assertEquals(new BigDecimal("100.00"),InvoiceCalculator.calculateTotalAmount(request));
    }
    @Test
    void  testCalculateTotalAmount_ZeroRate(){
        InvoiceRequest request = new InvoiceRequest();
        Item item = new Item();
        item.setRate(BigDecimal.ZERO);
        item.setQuantity(5);
        request.setItems(List.of(item));
        assertEquals(BigDecimal.ZERO,InvoiceCalculator.calculateTotalAmount(request));
    }

    @Test
    void  testCalculateTotalAmount_ZeroQuantity(){
        InvoiceRequest request = new InvoiceRequest();
        Item item = new Item();
        item.setRate(new BigDecimal("100.00"));
        item.setQuantity(0);
        request.setItems(List.of(item));
        assertEquals(new BigDecimal("0.00"),InvoiceCalculator.calculateTotalAmount(request));
    }


}
