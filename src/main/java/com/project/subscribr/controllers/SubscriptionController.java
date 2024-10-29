package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.orchestrators.SubscriptionOrchestrator;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                                         UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @GetMapping("/{subscriberId}")
    public ResponseEntity<List<User>> getSubscriptions(@PathVariable Long subscriberId) {
        System.out.println("Getting subscriptions for user: " + subscriberId);
        try {
            SubscriptionOrchestrator subscriptionOrchestrator = new SubscriptionOrchestrator(subscriptionService,
                    userService);

            List<User> subscriptions = subscriptionOrchestrator.getUserSubscriptions(subscriberId);

            System.out.println("Successfully retrieved subscriptions for user: " + subscriberId);
            return ResponseEntity.ok(subscriptions);
        } catch (UserNotFoundException exception) {
            System.out.println("Error: user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{subscriberId}/subscribe/{subscriptionToId}")
    public ResponseEntity<String> subscribe(@PathVariable Long subscriberId, @PathVariable Long subscriptionToId) {
        System.out.println("Subscribing user: " + subscriberId + " to user: " + subscriptionToId);
        try {
            SubscriptionOrchestrator subscriptionOrchestrator = new SubscriptionOrchestrator(subscriptionService,
                    userService);
            subscriptionOrchestrator.subscribeToUser(subscriberId, subscriptionToId);

            System.out.println("Successfully subscribed user: " + subscriberId + " to user: " + subscriptionToId);
            return ResponseEntity.ok("Subscription successful");
        } catch (UserNotFoundException exception) {
            System.out.println("Error: user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (AlreadySubscribedException exception) {
            System.out.println("Error: subscription already exists");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Already subscribed");
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }

    @PostMapping("/{subscriberId}/unsubscribe/{subscriptionToId}")
    public ResponseEntity<String> unsubscribe(@PathVariable Long subscriberId, @PathVariable Long subscriptionToId) {
        System.out.println("Unsubscribing user: " + subscriberId + " from user: " + subscriptionToId);
        try {
            SubscriptionOrchestrator subscriptionOrchestrator = new SubscriptionOrchestrator(subscriptionService, userService);
            subscriptionOrchestrator.unsubscribeToUser(subscriberId, subscriptionToId);

            System.out.println("Successfully unsubscribed user: " + subscriberId + " from user: " + subscriptionToId);
            return ResponseEntity.ok("Unsubscription successful");
        } catch (SubscriptionNotFoundException exception) {
            System.out.println("Error: subscription not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found.");
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
