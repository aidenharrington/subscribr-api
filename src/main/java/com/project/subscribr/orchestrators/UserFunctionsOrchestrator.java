package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class UserFunctionsOrchestrator {
    private final UserService userService;

    private final String SUBSCRIBR_VIDEO_UPLOADER_URL = "http://localhost:9000";

    // Subscribr URL: /{userId}/videos/{videoId}
    private final String UPLOAD_VIDEO_URL = "/videos/upload";

    private User user;

    private Video video;

    @Autowired
    public UserFunctionsOrchestrator(UserService userService) {
        this.userService = userService;
    }

    public void subscribeToUser(Long userId, Long subscriptionToId) throws UserNotFoundException, AlreadySubscribedException {
        if (this.user == null) {
            this.user = getUserFromDb(userId);
        }


        boolean alreadySubscribedToUser = this.user.getSubscriptions().stream().anyMatch(subscribedToUser ->
                subscribedToUser.getId().equals(subscriptionToId));

        if (!alreadySubscribedToUser) {
            userService.subscribeToUser(this.user.getId(), subscriptionToId);
        } else {
            throw new AlreadySubscribedException();
        }
    }

    public void unsubscribeToUser(Long userId, Long subscriptionToId) throws SubscriptionNotFoundException, UserNotFoundException {
        if (this.user == null) {
            this.user = getUserFromDb(userId);
        }

        userService.unsubscribeToUser(userId, subscriptionToId);
    }

    public User getUser(Long userId) throws UserNotFoundException {
        if (this.user == null) {
           this.user = getUserFromDb(userId);
        }

        return this.user;
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }

    private User getUserFromDb(Long userId) throws UserNotFoundException {
        return userService.getUserById(userId);
    }



    public void postVideo(Long videoUploaderId, Video video) {
        if (this.video == null) {
            this.video = populateVideo(videoUploaderId, video);
        }


        userService.postVideo(video);
        uploadVideo(video);
    }

    public Video populateVideo(Long uploaderUserId, Video video) {
        video.setReleaseDate(Timestamp.from(Instant.now()));
        video.setVideoUploaderId(uploaderUserId);

        return video;
    }

    private void uploadVideo(Video video) {
        String url = SUBSCRIBR_VIDEO_UPLOADER_URL + UPLOAD_VIDEO_URL;

        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
        String result = restTemplate.postForObject(url, video, String.class);
    }
}
