package com.project.subscribr.services;

import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.exceptions.VideoNotFoundException;
import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.models.repositories.SubscriptionRepository;
import com.project.subscribr.models.repositories.UserRepository;
import com.project.subscribr.models.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void subscribeToUser(Long userId, Long subscriptionToId) {
        Subscription subscription = new Subscription(userId, subscriptionToId);

        subscriptionRepository.save(subscription);
    }

    public void unsubscribeToUser(Long userId, Long subscriptionToId) throws SubscriptionNotFoundException {
        Long subscriptionId = subscriptionRepository.findByIds(userId, subscriptionToId)
                .orElseThrow(SubscriptionNotFoundException::new);

        subscriptionRepository.deleteById(subscriptionId);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void postVideo(Video video) {
        videoRepository.save(video);
    }

    public Video getVideoById(Long videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
    }

    public List<Long> getSubscribersToUser(Long userId) {
        return subscriptionRepository.findBySubscribedToId(userId);
    }




}
