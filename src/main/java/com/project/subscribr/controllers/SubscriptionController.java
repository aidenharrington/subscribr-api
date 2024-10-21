package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.SubscriptionNotFoundException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.orchestrators.SubscriptionOrchestrator;
import com.project.subscribr.orchestrators.UserFunctionsOrchestrator;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{userId}/subscribe/{subscriptionToId}")
    public ResponseEntity<String> subscribe(@PathVariable Long userId, @PathVariable Long subscriptionToId) {
        try {
            SubscriptionOrchestrator subscriptionOrchestrator = new SubscriptionOrchestrator(subscriptionService, userService);
            subscriptionOrchestrator.subscribeToUser(userId, subscriptionToId);

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
            SubscriptionOrchestrator subscriptionOrchestrator = new SubscriptionOrchestrator(subscriptionService, userService);
            subscriptionOrchestrator.unsubscribeToUser(userId, subscriptionToId);

            return ResponseEntity.ok("Unsubscription successful");
        } catch (SubscriptionNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
