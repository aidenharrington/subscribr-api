package com.project.subscribr.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "release_date")
    private Timestamp releaseDate;

    @Column(name = "video_uploader_id")
    private Long videoUploadId;

    
}
