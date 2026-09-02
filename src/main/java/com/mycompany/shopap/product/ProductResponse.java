/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.product;

/**
 *
 * @author Mateusz
 */
import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal netPrice;
    private BigDecimal vatRate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getNetPrice() { return netPrice; }
    public void setNetPrice(BigDecimal netPrice) { this.netPrice = netPrice; }

    public BigDecimal getVatRate() { return vatRate; }
    public void setVatRate(BigDecimal vatRate) { this.vatRate = vatRate; }
}
