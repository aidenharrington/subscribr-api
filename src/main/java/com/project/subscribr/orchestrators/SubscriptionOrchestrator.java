package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.Subscription;
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

    @Autowired
    public SubscriptionOrchestrator(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    public List<User> getUserSubscriptions(Long subscriberId) throws UserNotFoundException {
        return subscriptionService.getUserSubscriptions(subscriberId);
    }

    public void subscribeToUser(Long subscriberId, Long subscriptionToId) throws UserNotFoundException, AlreadySubscribedException {
        List<User> userSubscriptions = subscriptionService.getUserSubscriptions(subscriberId);
        User user = userService.getUserById(subscriberId);


        boolean alreadySubscribedToUser = userSubscriptions.stream().anyMatch(subscribedToUser ->
                subscribedToUser.getId().equals(subscriptionToId));

        if (!alreadySubscribedToUser) {
            User subscribeToUser = this.userService.getUserById(subscriptionToId);
            subscriptionService.subscribeToUser(user, subscribeToUser);
        } else {
            throw new AlreadySubscribedException();
        }
    }

    public void unsubscribeToUser(Long subscriberId, Long subscriptionToId) throws SubscriptionNotFoundException {
        subscriptionService.unsubscribeToUser(subscriberId, subscriptionToId);
    }
}
