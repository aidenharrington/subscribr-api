package com.project.subscribr;

import com.project.subscribr.orchestrators.*;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.UserService;
import com.project.subscribr.services.VideoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public NewUserOrchestrator newUserOrchestrator(UserService userService) {
        return new NewUserOrchestrator(userService);
    }

    @Bean
    public SubscriptionOrchestrator subscriptionOrchestrator(SubscriptionService subscriptionService, UserService userService) {
        return new SubscriptionOrchestrator(subscriptionService, userService);
    }

    @Bean
    public UserFunctionsOrchestrator userFunctionsOrchestrator(UserService userService) {
        return new UserFunctionsOrchestrator(userService);
    }

    @Bean
    public VideoOrchestrator videoOrchestrator(UserService userService, VideoService videoService) {
        return new VideoOrchestrator(userService, videoService);
    }

    @Bean
    public VideoUploadedWebhookOrchestrator videoUploadedWebhookOrchestrator(SubscriptionService subscriptionService, VideoService videoService) {
        return new VideoUploadedWebhookOrchestrator(subscriptionService, videoService);
    }
}
