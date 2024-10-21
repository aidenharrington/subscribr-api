package com.project.subscribr.controllers;

import com.project.subscribr.orchestrators.VideoUploadedWebhookOrchestrator;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.UserService;
import com.project.subscribr.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    private final SubscriptionService subscriptionService;
    private final VideoService videoService;

    @Autowired
    public WebhookController(SubscriptionService subscriptionService, VideoService videoService) {
        this.subscriptionService = subscriptionService;
        this.videoService = videoService;
    }

    @PostMapping("/{userId}/videos/{videoId}")
    public String videoUploadComplete(@PathVariable Long userId, @PathVariable Long videoId) {
        try {
            VideoUploadedWebhookOrchestrator webhookOrchestrator =
                    new VideoUploadedWebhookOrchestrator(subscriptionService, videoService);

            System.out.println("Received webhook for video upload.");

            webhookOrchestrator.populateVideo(userId, videoId);
            webhookOrchestrator.sendWebhookUpdates();

            return "Successfully received webhook and sent alerts";
        } catch (Exception exception) {
            return "Successfully received webhook with internal error";
        }

    }

}
