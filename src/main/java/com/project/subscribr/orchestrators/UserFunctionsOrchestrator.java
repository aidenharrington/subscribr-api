package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFunctionsOrchestrator {
    private final UserService userService;

    @Autowired
    public UserFunctionsOrchestrator(UserService userService) {
        this.userService = userService;
    }

    public User getUser(Long userId) throws UserNotFoundException {
        return userService.getUserById(userId);
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }
}
