package com.project.subscribr.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.subscribr.exceptions.AccountNotFoundException;
import com.project.subscribr.models.Video;
import com.project.subscribr.queries.AccountDB;

@RestController
@RequestMapping("/users")
public class AccountController {
    
    @GetMapping("/test")
    public String test() {
        return "Success-01";
    }

    @PostMapping("/{id}/subscribe/{subscriptionAccountId}")
    public ResponseEntity<String> subscribe(@PathVariable String id, @PathVariable String subscriptionAccountId) {
        // try {
        //     AccountDB.subscribeToAccount(id, subscriptionAccountId);
        //     return ResponseEntity.ok("Success");
        // } catch (AccountNotFoundException exception) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail");
        // } catch (Exception exception) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        // }

        return ResponseEntity.ok("Success-00");
    }


    @PostMapping("{id}/post-video")
    public ResponseEntity<String> postVideo(@PathVariable int id, @RequestBody Video video) {
        try {
            AccountDB.postVideo(id, video);
            return ResponseEntity.ok("Success");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
