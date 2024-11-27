package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.DTOs.VideoDTO;
import com.project.subscribr.orchestrators.VideoOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("{userId}/post-video")
    public ResponseEntity<String> postVideo(@PathVariable Long userId, @RequestBody VideoDTO videoDTO) {
        System.out.println("Posting video, upload started: " + videoDTO.getName() + " for user: " + userId);
        try {
            VideoOrchestrator videoOrchestrator = applicationContext.getBean(VideoOrchestrator.class);
            videoOrchestrator.postVideo(videoDTO);

            System.out.println("Video upload successfully started: " + videoDTO.getName());
            return ResponseEntity.ok("Success - video upload started");
        } catch (UserNotFoundException exception) {
            System.out.println("Error: user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
