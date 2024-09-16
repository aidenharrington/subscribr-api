package com.project.subscribr.services;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.models.repositories.SubscriptionRepository;
import com.project.subscribr.models.repositories.UserRepository;
import com.project.subscribr.models.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository,
                       VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.videoRepository = videoRepository;
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

    public void postVideo(Video video) {
        videoRepository.save(video);
    }



}
