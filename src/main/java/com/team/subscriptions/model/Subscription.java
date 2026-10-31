package com.team.subscriptions.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="subscriptions")
public class Subscription {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Customer customer;
    @ManyToOne(optional=false) private Plan plan;
    @Enumerated(EnumType.STRING) private SubscriptionStatus status = SubscriptionStatus.DRAFT;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private Integer quantity = 1;
    public Subscription(){}
    public Long getId(){return id;} public Customer getCustomer(){return customer;} public void setCustomer(Customer v){customer=v;}
    public Plan getPlan(){return plan;} public void setPlan(Plan v){plan=v;}
    public SubscriptionStatus getStatus(){return status;} public void setStatus(SubscriptionStatus v){status=v;}
    public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
    public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
    public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
}
