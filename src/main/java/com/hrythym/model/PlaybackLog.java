package com.hrythym.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playback_logs")
public class PlaybackLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long songId;
    private String action;
    private String genre;
    private String mood;
    private LocalDateTime timestamp;

    // --- Getters & setters ---

    public Long getId() { return id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
