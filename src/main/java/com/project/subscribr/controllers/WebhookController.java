package com.project.subscribr.controllers;

import com.project.subscribr.models.entities.Video;
import com.project.subscribr.orchestrators.VideoUploadedWebhookOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/webhooks")
public class WebhookController {

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("/{userId}/videos/{videoId}")
    public String videoUploadComplete(@PathVariable Long userId, @PathVariable Long videoId) {
        System.out.println("Received webhook for video upload.");

        try {
            VideoUploadedWebhookOrchestrator webhookOrchestrator = applicationContext.getBean(VideoUploadedWebhookOrchestrator.class);

            Video video = webhookOrchestrator.populateVideo(videoId);
            webhookOrchestrator.sendWebhookUpdates(userId, video);

            return "Successfully received webhook and sent alerts";
        } catch (Exception exception) {
            return "Successfully received webhook with internal error";
        }

    }

}
