package com.project.subscribr.controllers;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.DTOs.VideoDTO;
import com.project.subscribr.orchestrators.UserFunctionsOrchestrator;
import com.project.subscribr.orchestrators.VideoOrchestrator;
import com.project.subscribr.services.UserService;
import com.project.subscribr.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/videos")
public class VideoController {

    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public VideoController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @PostMapping("{userId}/post-video")
    public ResponseEntity<String> postVideo(@PathVariable Long userId, @RequestBody VideoDTO videoDTO) {
        try {
            VideoOrchestrator videoOrchestrator = new VideoOrchestrator(userService, videoService);
            videoOrchestrator.postVideo(videoDTO);

            return ResponseEntity.ok("Success - video upload started");
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
}
