package com.project.subscribr.endpoints;

import com.project.subscribr.exceptions.AlreadySubscribedException;
import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.orchestrators.UserFunctionsOrchestrator;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.subscribr.models.entities.Video;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);

            User user = userFunctionsOrchestrator.populateUser(id);

            return ResponseEntity.ok(user);
        } catch (UserNotFoundException exception) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
         } catch (Exception exception) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }

    @PostMapping("/{id}/subscribe/{subscriptionToId}")
    public ResponseEntity<String> subscribe(@PathVariable String id, @PathVariable String subscriptionToId) {
         try {
             UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(userService);
             userFunctionsOrchestrator.populateUser(id);
             userFunctionsOrchestrator.subscribeToUser(subscriptionToId);

             return ResponseEntity.ok("Subscription successful");
         } catch (UserNotFoundException exception) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
         } catch (AlreadySubscribedException exception) {
             return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Already subscribed");
         } catch (Exception exception) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
         }
    }


//    @PostMapping("{id}/post-video")
//    public ResponseEntity<String> postVideo(@PathVariable int id, @RequestBody Video video) {
//        try {
//            UserFunctionsOrchestrator.postVideo(id, video);
//            return ResponseEntity.ok("Success");
//        } catch (Exception exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
//        }
//    }
}
