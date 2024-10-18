package com.project.subscribr.models.entities;

import com.project.subscribr.models.repositories.UserRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Entity
@Table(name = "videos")
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "release_date")
    private Timestamp releaseDate;

    @Column(name = "video_uploader_id", nullable = false)
    private Long videoUploaderId;

    @Transient
    private String uploaderUsername;
}
