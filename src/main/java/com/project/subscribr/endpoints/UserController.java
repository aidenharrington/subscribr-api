package com.project.subscribr.endpoints;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.orchestrators.UserFunctionsOrchestrator;
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


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            UserFunctionsOrchestrator userFunctionsOrchestrator = new UserFunctionsOrchestrator(id);

            return ResponseEntity.ok(userFunctionsOrchestrator.getUser());
        } catch (UserNotFoundException exception) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
         } catch (Exception exception) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }

    @PostMapping("/{id}/subscribe/{subscriptionUserId}")
    public ResponseEntity<String> subscribe(@PathVariable String id, @PathVariable String subscriptionUserId) {
        // try {
        //     UserDB.subscribeToUser(id, subscriptionUserId);
        //     return ResponseEntity.ok("Success");
        // } catch (UserNotFoundException exception) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail");
        // } catch (Exception exception) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        // }

        return ResponseEntity.ok("Success-00");
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
