package com.hrythym.repository;

import com.hrythym.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    // Optional helper for CreatePlaylistAction—find by mood & genre
    List<Song> findByMoodAndGenre(String mood, String genre);
}
