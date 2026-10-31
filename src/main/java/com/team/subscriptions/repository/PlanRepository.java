package com.team.subscriptions.repository;
import com.team.subscriptions.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlanRepository extends JpaRepository<Plan, Long> {}
