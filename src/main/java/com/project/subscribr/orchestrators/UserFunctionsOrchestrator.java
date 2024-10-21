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
    private User user;

    @Autowired
    public UserFunctionsOrchestrator(UserService userService) {
        this.userService = userService;
    }

    public User getUser(Long userId) throws UserNotFoundException {
        if (this.user == null) {
           this.user = userService.getUserById(userId);
        }

        return this.user;
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }
}
