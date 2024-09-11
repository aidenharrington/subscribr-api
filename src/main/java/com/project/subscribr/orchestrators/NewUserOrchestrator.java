package com.project.subscribr.orchestrators;

import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.requestBodies.UserRequestBody;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUserOrchestrator {
    private final UserService userService;

    @Autowired
    public NewUserOrchestrator(UserService userService) {
        this.userService = userService;
    }

    public User createUser(UserRequestBody newUser) {
        User user = mapUserRequestBodyToUser(newUser);

        userService.createUser(user);

        return user;
    }

    private User mapUserRequestBodyToUser(UserRequestBody newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());

        return user;
    }
}
