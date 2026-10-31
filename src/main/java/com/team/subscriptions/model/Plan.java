package com.team.subscriptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(name = "billing_interval")
    @Enumerated(EnumType.STRING)
    private BillingInterval interval = BillingInterval.MONTHLY;

    @PositiveOrZero
    private BigDecimal price = BigDecimal.ZERO;

    private Integer minimumQuantity = 1;

    private LocalDate availableFrom;

    private LocalDate availableUntil;

    private boolean autoClose = false;

    public Plan() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        name = v;
    }

    public BillingInterval getInterval() {
        return interval;
    }

    public void setInterval(BillingInterval v) {
        interval = v;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal v) {
        price = v;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer v) {
        minimumQuantity = v;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate v) {
        availableFrom = v;
    }

    public LocalDate getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalDate v) {
        availableUntil = v;
    }

    public boolean isAutoClose() {
        return autoClose;
    }

    public void setAutoClose(boolean v) {
        autoClose = v;
    }
}