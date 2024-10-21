package com.project.subscribr.services;

import com.project.subscribr.exceptions.UserNotFoundException;
import com.project.subscribr.exceptions.VideoNotFoundException;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.models.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    private final UserService userService;
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(UserService userService, VideoRepository videoRepository) {
        this.userService = userService;
        this.videoRepository = videoRepository;
    }


    public void postVideo(Video video) {
        videoRepository.save(video);
    }

    public Video getVideoById(Long videoId) throws VideoNotFoundException {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        String uploaderUsername = null;

        try {
            uploaderUsername = userService.getUserById(video.getVideoUploaderId()).getUsername();
        } catch (UserNotFoundException e) {
            // Ignored, username is defaulted to null
        }

        video.setUploaderUsername(uploaderUsername);
        return video;
    }
}
