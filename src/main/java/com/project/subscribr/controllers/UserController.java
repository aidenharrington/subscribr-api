package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.exceptions.UsernameAlreadyExistsException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.orchestrators.NewUserOrchestrator;
import com.project.subscribr.orchestrators.UserFunctionsOrchestrator;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);

            List<User> users = userFunctionsOrchestrator.getUsers();

            return ResponseEntity.ok(users);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        System.out.println("Retrieving user: " + userId);
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);

            User user = userFunctionsOrchestrator.getUser(userId);

            System.out.println("Successfully retrieved user: " + user.getId());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException exception) {
            System.out.println("Error: user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
         } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        System.out.println("Creating new user: " + newUser);
        try {
            NewUserOrchestrator newUserOrchestrator = new NewUserOrchestrator(userService);

            User user = newUserOrchestrator.createUser(newUser);
            System.out.println("Successfully created new user: " + user.getId());
            return ResponseEntity.ok(user);
        } catch (UsernameAlreadyExistsException exception) {
            System.out.println("Error: username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
