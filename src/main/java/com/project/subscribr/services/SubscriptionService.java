package com.project.subscribr.services;

import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void subscribeToUser(User user, User userToSubscribeTo) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(user);
        subscription.setSubscribedTo(userToSubscribeTo);

        subscriptionRepository.save(subscription);
    }

    public void unsubscribeToUser(Long userId, Long subscriptionToId) throws SubscriptionNotFoundException {
        Long subscriptionId = subscriptionRepository.findSubscriberIdBySubscription(userId, subscriptionToId)
                .orElseThrow(SubscriptionNotFoundException::new);

        subscriptionRepository.deleteById(subscriptionId);
    }

    public List<User> getSubscribersToUser(Long subscribedToId) {
        return subscriptionRepository.findSubscribersBySubscribedToId(subscribedToId);
    }

    public List<User> getUserSubscriptions(Long subscriberId) {
        return subscriptionRepository.findSubscribedUsersBySubscriberId(subscriberId);
    }
}
