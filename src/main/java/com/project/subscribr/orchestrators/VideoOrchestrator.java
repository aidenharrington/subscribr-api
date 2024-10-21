package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.models.DTOs.VideoDTO;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.services.UserService;
import com.project.subscribr.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class VideoOrchestrator {

    private final String UPLOAD_VIDEO_URL = "/videos/upload";
    private final String SUBSCRIBR_VIDEO_UPLOADER_URL = "http://localhost:9000";

    private final UserService userService;
    private final VideoService videoService;
    private Video video;

    @Autowired
    public VideoOrchestrator(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }



    public void postVideo(VideoDTO videoDTO) throws UserNotFoundException {
        populateVideo(videoDTO);
        videoService.postVideo(video);
        uploadVideo(video);
    }

    private void uploadVideo(Video video) {
        String url = SUBSCRIBR_VIDEO_UPLOADER_URL + UPLOAD_VIDEO_URL;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, video, String.class);
    }

    private void populateVideo(VideoDTO videoDTO) throws UserNotFoundException {
        if (this.video == null) {
            String uploaderUsername = userService.getUserById(videoDTO.getVideoUploaderId()).getUsername();
            Video video = new Video();

            video.setName(videoDTO.getName());
            video.setVideoUploaderId(videoDTO.getVideoUploaderId());
            video.setUploaderUsername(uploaderUsername);
            video.setReleaseDate(Timestamp.from(Instant.now()));

            this.video = video;
        }
    }
}
