package com.project.subscribr.models.repositories;

import com.project.subscribr.models.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
