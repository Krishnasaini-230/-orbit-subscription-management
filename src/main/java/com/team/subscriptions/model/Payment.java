package com.team.subscriptions.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="payments")
public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Invoice invoice;
    private BigDecimal amount;
    private LocalDate paymentDate = LocalDate.now();
    private String method;
    private String note;
    public Payment(){}
    public Long getId(){return id;} public Invoice getInvoice(){return invoice;} public void setInvoice(Invoice v){invoice=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public LocalDate getPaymentDate(){return paymentDate;} public void setPaymentDate(LocalDate v){paymentDate=v;}
    public String getMethod(){return method;} public void setMethod(String v){method=v;}
    public String getNote(){return note;} public void setNote(String v){note=v;}
}
