package com.team.subscriptions.repository;
import com.team.subscriptions.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {}
