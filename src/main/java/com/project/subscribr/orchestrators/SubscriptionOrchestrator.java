package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionOrchestrator {
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    private User user;
    private List<User> subscriptions;

    @Autowired
    public SubscriptionOrchestrator(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    public List<User> getUserSubscriptions(Long subscriberId) throws UserNotFoundException {
        setupOrchestrator(subscriberId);

        return this.subscriptions;
    }

    public void subscribeToUser(Long subscriberId, Long subscriptionToId) throws UserNotFoundException, AlreadySubscribedException {
        setupOrchestrator(subscriberId);


        boolean alreadySubscribedToUser = this.subscriptions.stream().anyMatch(subscribedToUser ->
                subscribedToUser.getId().equals(subscriptionToId));

        if (!alreadySubscribedToUser) {
            User subscribeToUser = this.userService.getUserById(subscriptionToId);
            subscriptionService.subscribeToUser(this.user, subscribeToUser);
        } else {
            throw new AlreadySubscribedException();
        }
    }

    public void unsubscribeToUser(Long subscriberId, Long subscriptionToId) throws SubscriptionNotFoundException, UserNotFoundException {
        setupOrchestrator(subscriberId);

        subscriptionService.unsubscribeToUser(subscriberId, subscriptionToId);
    }

    private void setupOrchestrator(Long subscriberId) throws UserNotFoundException {
        populateUser(subscriberId);
        populateSubscriptions(subscriberId);
    }

    private void populateUser(Long subscriberId) throws UserNotFoundException {
        if (this.user == null) {
            this.user = userService.getUserById(subscriberId);
        }
    }

    private void populateSubscriptions(Long subscriberId) throws UserNotFoundException {
        if (this.subscriptions == null) {
            this.subscriptions = subscriptionService.getUserSubscriptions(subscriberId);
        }
    }

}
