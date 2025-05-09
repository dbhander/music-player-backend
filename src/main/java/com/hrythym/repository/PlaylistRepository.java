package com.hrythym.repository;

import com.hrythym.model.Playlist;
import com.hrythym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
    List<Playlist> findByOwner(User owner);
}
