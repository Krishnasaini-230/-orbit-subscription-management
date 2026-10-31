
package com.team.subscriptions.service;

import com.team.subscriptions.model.*;
import com.team.subscriptions.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BusinessService {

    private final CustomerRepository customers;
    private final ProductRepository products;
    private final PlanRepository plans;
    private final SubscriptionRepository subscriptions;
    private final InvoiceRepository invoices;
    private final PaymentRepository payments;

    public BusinessService(
            CustomerRepository customers,
            ProductRepository products,
            PlanRepository plans,
            SubscriptionRepository subscriptions,
            InvoiceRepository invoices,
            PaymentRepository payments) {

        this.customers = customers;
        this.products = products;
        this.plans = plans;
        this.subscriptions = subscriptions;
        this.invoices = invoices;
        this.payments = payments;
    }

    public List<Customer> customers() {
        return customers.findAll();
    }

    public List<Product> products() {
        return products.findAll();
    }

    public List<Plan> plans() {
        return plans.findAll();
    }

    public List<Subscription> subscriptions() {
        return subscriptions.findAll();
    }

    public List<Invoice> invoices() {
        return invoices.findAll();
    }

    public List<Payment> payments() {
        return payments.findAll();
    }

    public long activeSubscriptions() {
        return subscriptions.findAll().stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .count();
    }

    public BigDecimal collected() {
        return payments.findAll().stream()
                .map(Payment::getAmount)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal outstanding() {
        return invoices.findAll().stream()
                .map(Invoice::getOutstanding)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long overdueCount() {
        return invoices.findAll().stream()
                .filter(i -> i.getDueDate() != null
                        && i.getDueDate().isBefore(LocalDate.now())
                        && i.getOutstanding().compareTo(BigDecimal.ZERO) > 0)
                .count();
    }

    public Customer saveCustomer(Customer c) {
        return customers.save(c);
    }

    public Product saveProduct(Product p) {
        return products.save(p);
    }

    public Plan savePlan(Plan p) {
        return plans.save(p);
    }

    public Subscription saveSubscription(Subscription s) {
        return subscriptions.save(s);
    }

    public Customer customer(Long id) {
        return customers.findById(id).orElseThrow();
    }

    public Plan plan(Long id) {
        return plans.findById(id).orElseThrow();
    }

    public Invoice invoice(Long id) {
        return invoices.findById(id).orElseThrow();
    }

    @Transactional
    public Invoice createInvoice(Long subscriptionId) {

        Subscription s = subscriptions.findById(subscriptionId).orElseThrow();

        if (s.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only active subscriptions can be invoiced."
            );
        }

        boolean already = invoices.findAll().stream()
                .anyMatch(i -> i.getSubscription() != null
                        && i.getSubscription().getId().equals(subscriptionId)
                        && i.getInvoiceDate().equals(LocalDate.now()));

        if (already) {
            throw new IllegalStateException(
                    "An invoice has already been generated for this subscription today."
            );
        }

        Invoice i = new Invoice();
        i.setCustomer(s.getCustomer());
        i.setSubscription(s);
        i.setTotal(
                s.getPlan().getPrice()
                        .multiply(BigDecimal.valueOf(s.getQuantity()))
        );

        return invoices.save(i);
    }

    @Transactional
    public Payment recordPayment(
            Long invoiceId,
            BigDecimal amount,
            String method,
            String note) {

        Invoice i = invoice(invoiceId);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment must be greater than zero."
            );
        }

        if (amount.compareTo(i.getOutstanding()) > 0) {
            throw new IllegalArgumentException(
                    "Payment cannot exceed the outstanding balance."
            );
        }

        Payment p = new Payment();
        p.setInvoice(i);
        p.setAmount(amount);
        p.setMethod(method);
        p.setNote(note);

        payments.save(p);

        i.setAmountPaid(i.getAmountPaid().add(amount));

        i.setStatus(
                i.getOutstanding().compareTo(BigDecimal.ZERO) == 0
                        ? InvoiceStatus.PAID
                        : InvoiceStatus.PARTIALLY_PAID
        );

        invoices.save(i);

        return p;
    }

    public void transition(Long id, SubscriptionStatus next) {

        Subscription s = subscriptions.findById(id).orElseThrow();

        boolean allowed = switch (s.getStatus()) {

            case DRAFT ->
                    next == SubscriptionStatus.QUOTATION
                            || next == SubscriptionStatus.CLOSED;

            case QUOTATION ->
                    next == SubscriptionStatus.CONFIRMED
                            || next == SubscriptionStatus.CLOSED;

            case CONFIRMED ->
                    next == SubscriptionStatus.ACTIVE
                            || next == SubscriptionStatus.CLOSED;

            case ACTIVE ->
                    next == SubscriptionStatus.PAUSED
                            || next == SubscriptionStatus.CLOSED;

            case PAUSED ->
                    next == SubscriptionStatus.ACTIVE
                            || next == SubscriptionStatus.CLOSED;

            case CLOSED -> false;
        };

        if (!allowed) {
            throw new IllegalStateException(
                    "That subscription status change is not allowed."
            );
        }

        s.setStatus(next);
        subscriptions.save(s);
    }
}
