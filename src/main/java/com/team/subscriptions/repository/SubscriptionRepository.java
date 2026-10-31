package com.team.subscriptions.repository;
import com.team.subscriptions.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {}
