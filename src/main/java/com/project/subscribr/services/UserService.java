package com.project.subscribr.services;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.repositories.SubscriptionRepository;
import com.project.subscribr.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void subscribeToUser(Long userId, Long subscriptionToId) {
        Subscription subscription = new Subscription(userId, subscriptionToId);

        subscriptionRepository.save(subscription);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }



}
