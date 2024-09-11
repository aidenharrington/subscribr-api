package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFunctionsOrchestrator {
    private final UserService userService;

    @Getter
    private User user;

    @Autowired
    public UserFunctionsOrchestrator(UserService userService) {
        this.userService = userService;
    }

    // User must be setup before operations can be run.
    // Populates User object with DB values for user with {id}
    public User populateUser(String id) throws UserNotFoundException {
        try {
            this.user = getUser(id);

            return user;
        } catch (NumberFormatException exception) {
            // Any issues converting string id to long indicates incorrect id.
            // For the client, this is the same as no user found by id.
            throw new UserNotFoundException();
        }
    }

    // Aiden - todo
    public void subscribeToUser(String id) throws UserNotFoundException, AlreadySubscribedException {
        Long subscriptionToId = Long.valueOf(id);

        boolean alreadySubscribedToUser = this.user.getSubscriptions().stream().anyMatch(subscribedToUser ->
                subscribedToUser.getId().equals(subscriptionToId));

        if (!alreadySubscribedToUser) {
            userService.subscribeToUser(this.user.getId(), subscriptionToId);
        } else {
            throw new AlreadySubscribedException();
        }
    }

    private User getUser(String id) throws UserNotFoundException {
        Long userId = Long.valueOf(id);

        return userService.getUserById(userId);
    }

    // Aiden - todo
//    public postVideo(int userId, Video video) {
//        // Todo
//    }
}
