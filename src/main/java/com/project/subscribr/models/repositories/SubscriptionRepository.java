package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.subscriberId FROM Subscription s WHERE s.subscribedToId = :subscribedToId")
    List<Long> findBySubscribedToId(Long subscribedToId);
}
