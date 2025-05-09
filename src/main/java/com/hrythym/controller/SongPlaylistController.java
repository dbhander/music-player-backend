package com.hrythym.controller;

import com.hrythym.model.Song;
import com.hrythym.model.Playlist;
import com.hrythym.model.User;
import com.hrythym.repository.SongRepository;
import com.hrythym.repository.PlaylistRepository;
import com.hrythym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongPlaylistController {

    @Autowired private SongRepository songRepo;
    @Autowired private PlaylistRepository playlistRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songRepo.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Playlist>> getUserPlaylists(
            @AuthenticationPrincipal UserDetails principal
    ) {
        User user = userRepo.findByUsername(principal.getUsername()).get();
        List<Playlist> playlists = playlistRepo.findByOwner(user);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/playlists/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        return playlistRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
