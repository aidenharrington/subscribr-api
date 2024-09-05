package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
