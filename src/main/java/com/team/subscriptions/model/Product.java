package com.team.subscriptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    private String productType = "Service";
    @PositiveOrZero private BigDecimal salesPrice = BigDecimal.ZERO;
    @PositiveOrZero private BigDecimal costPrice = BigDecimal.ZERO;
    private String notes;
    private boolean active = true;
    public Product(){}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getProductType(){return productType;} public void setProductType(String v){productType=v;}
    public BigDecimal getSalesPrice(){return salesPrice;} public void setSalesPrice(BigDecimal v){salesPrice=v;}
    public BigDecimal getCostPrice(){return costPrice;} public void setCostPrice(BigDecimal v){costPrice=v;}
    public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
