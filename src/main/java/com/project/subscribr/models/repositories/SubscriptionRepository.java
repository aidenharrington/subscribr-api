package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.id FROM Subscription s WHERE s.subscriberId = :subscriberId AND s.subscribedToId = :subscribedToId")
    Optional<Long> findById(Long subscriberId, Long subscribedToId);

    @Query("SELECT s FROM Subscription s WHERE s.subscribedToId = :subscribedToId")
    List<User> findBySubscribedToUser(Long subscribedToId);

    @NonNull
    void deleteById(@NonNull Long subscriptionId);
}
