/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.product;

/**
 *
 * @author Mateusz
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductCreateRequest {

    @NotBlank
    private String name;

    @Positive
    private BigDecimal netPrice;

    @PositiveOrZero
    private BigDecimal vatRate;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getNetPrice() { return netPrice; }
    public void setNetPrice(BigDecimal netPrice) { this.netPrice = netPrice; }

    public BigDecimal getVatRate() { return vatRate; }
    public void setVatRate(BigDecimal vatRate) { this.vatRate = vatRate; }
}
