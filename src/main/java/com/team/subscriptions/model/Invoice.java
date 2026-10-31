package com.team.subscriptions.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="invoices")
public class Invoice {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Customer customer;
    @ManyToOne private Subscription subscription;
    private LocalDate invoiceDate = LocalDate.now();
    private LocalDate dueDate = LocalDate.now().plusDays(15);
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal amountPaid = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING) private InvoiceStatus status = InvoiceStatus.CONFIRMED;
    public Invoice(){}
    public Long getId(){return id;} public Customer getCustomer(){return customer;} public void setCustomer(Customer v){customer=v;}
    public Subscription getSubscription(){return subscription;} public void setSubscription(Subscription v){subscription=v;}
    public LocalDate getInvoiceDate(){return invoiceDate;} public void setInvoiceDate(LocalDate v){invoiceDate=v;}
    public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate v){dueDate=v;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
    public BigDecimal getAmountPaid(){return amountPaid;} public void setAmountPaid(BigDecimal v){amountPaid=v;}
    public InvoiceStatus getStatus(){return status;} public void setStatus(InvoiceStatus v){status=v;}
    public BigDecimal getOutstanding(){return total.subtract(amountPaid);}
}
