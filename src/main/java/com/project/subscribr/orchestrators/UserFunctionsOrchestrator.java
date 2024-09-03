package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.services.UserService;
import lombok.Getter;

public class UserFunctionsOrchestrator {
    private UserService userService;
    @Getter
    private User user;

    public UserFunctionsOrchestrator(String userId) throws UserNotFoundException {
        this.userService = new UserService();
        this.user = populateUser(userId);
    }

    // Aiden - todo
    public void subscribeToUser(Long subscriptionUserId) throws UserNotFoundException {
        User subscribtionUser = userService.getUserById(subscriptionUserId);


    }

    // Aiden - todo
//    public postVideo(int userId, Video video) {
//        // Todo
//    }

    private User populateUser(String id) throws UserNotFoundException {
        try {
            Long userId = Long.valueOf(id);
            User user = userService.getUserById(userId);

            user.setUserId(userId);
            return user;
        } catch (NumberFormatException exception) {
            // Any issues converting string id to long indicates incorrect id.
            // For the client, this is the same as no user found by id.
            throw new UserNotFoundException();
        }
    }
}
