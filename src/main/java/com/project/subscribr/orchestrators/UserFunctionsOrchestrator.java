package com.project.subscribr.orchestrators;

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
    public UserFunctionsOrchestrator(UserService userService) throws UserNotFoundException {
        this.userService = userService;
    }

    // Aiden - todo
    public void subscribeToUser(Long subscriptionUserId) throws UserNotFoundException {
        User subscribtionUser = userService.getUserById(subscriptionUserId);


    }

    // Aiden - todo
//    public postVideo(int userId, Video video) {
//        // Todo
//    }

    public User populateUser(String id) throws UserNotFoundException {
        try {
            Long userId = Long.valueOf(id);
            User user = userService.getUserById(userId);

            user.setId(userId);
            this.user = user;
            return user;
        } catch (NumberFormatException exception) {
            // Any issues converting string id to long indicates incorrect id.
            // For the client, this is the same as no user found by id.
            throw new UserNotFoundException();
        }
    }
}
