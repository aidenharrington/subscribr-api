package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UsernameAlreadyExistsException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
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
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);

            User user = userFunctionsOrchestrator.getUser(userId);

            return ResponseEntity.ok(user);
        } catch (UserNotFoundException exception) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
         } catch (Exception exception) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        try {
            NewUserOrchestrator newUserOrchestrator = new NewUserOrchestrator(userService);

            User user = newUserOrchestrator.createUser(newUser);
            return ResponseEntity.ok(user);
        } catch (UsernameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{userId}/subscribe/{subscriptionToId}")
    public ResponseEntity<String> subscribe(@PathVariable Long userId, @PathVariable Long subscriptionToId) {
         try {
             UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);
             userFunctionsOrchestrator.subscribeToUser(userId, subscriptionToId);

             return ResponseEntity.ok("Subscription successful");
         } catch (UserNotFoundException exception) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
         } catch (AlreadySubscribedException exception) {
             return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Already subscribed");
         } catch (Exception exception) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
         }
    }

    @PostMapping("/{userId}/unsubscribe/{subscriptionToId}")
    public ResponseEntity<String> unsubscribe(@PathVariable Long userId, @PathVariable Long subscriptionToId) {
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);
            userFunctionsOrchestrator.unsubscribeToUser(userId, subscriptionToId);

            return ResponseEntity.ok("Unsubscription successful");
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (SubscriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Subscription not found.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }


    @PostMapping("{userId}/post-video")
    public ResponseEntity<String> postVideo(@PathVariable Long userId, @RequestBody Video newVideo) {
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);
            userFunctionsOrchestrator.postVideo(userId, newVideo);

            return ResponseEntity.ok("Success - video upload started");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
