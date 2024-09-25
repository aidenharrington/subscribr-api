CREATE TABLE videos (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    release_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    video_uploader_id INT NOT NULL,
    FOREIGN KEY (video_uploader_id) REFERENCES users(id) ON DELETE CASCADE,
);