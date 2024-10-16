package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.UsernameAlreadyExistsException;
import com.project.subscribr.models.entities.User;
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

    public User createUser(User user) throws UsernameAlreadyExistsException {
        userService.createUser(user);

        return user;
    }
}
