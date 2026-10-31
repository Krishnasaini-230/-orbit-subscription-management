package com.team.subscriptions.repository;
import com.team.subscriptions.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {}
