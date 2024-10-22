package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.id FROM Subscription s WHERE s.subscriber.id = :subscriberId AND s.subscribedTo.id = :subscribedToId")
    Optional<Long> findSubscriberIdBySubscription(Long subscriberId, Long subscribedToId);

    @Query("SELECT s.subscriber FROM Subscription s WHERE s.subscribedTo.id = :subscribedToId")
    List<User> findSubscribersBySubscribedToId(Long subscribedToId);

    @Query("SELECT s.subscribedTo FROM Subscription s WHERE s.subscriber.id = :subscriberId")
    List<User> findSubscribedUsersBySubscriberId(Long subscriberId);

    @NonNull
    void deleteById(@NonNull Long subscriptionId);
}
