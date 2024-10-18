package com.project.subscribr.services;

import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UsernameAlreadyExistsException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.exceptions.VideoNotFoundException;
import com.project.subscribr.models.entities.Subscription;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.models.repositories.SubscriptionRepository;
import com.project.subscribr.models.repositories.UserRepository;
import com.project.subscribr.models.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Subscription subscription = new Subscription();
        subscription.setSubscriberId(userId);
        subscription.setSubscribedToId(subscriptionToId);

        subscriptionRepository.save(subscription);
    }

    public void unsubscribeToUser(Long userId, Long subscriptionToId) throws SubscriptionNotFoundException {
        Long subscriptionId = subscriptionRepository.findById(userId, subscriptionToId)
                .orElseThrow(SubscriptionNotFoundException::new);

        subscriptionRepository.deleteById(subscriptionId);
    }

    public void createUser(User user) throws UsernameAlreadyExistsException {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException();
        }

    }

    public void postVideo(Video video) {
        videoRepository.save(video);
    }

    public Video getVideoById(Long videoId) throws VideoNotFoundException {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        String uploaderUsername = null;

        try {
            uploaderUsername = getUserById(video.getVideoUploaderId()).getUsername();
        } catch (UserNotFoundException e) {
            // Ignored, username is defaulted to null
        }

        video.setUploaderUsername(uploaderUsername);
        return video;
    }

    public List<Long> getSubscribersToUser(Long userId) {
        return subscriptionRepository.findBySubscribedToId(userId);
    }




}
