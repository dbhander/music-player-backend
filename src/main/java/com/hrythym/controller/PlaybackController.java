package com.hrythym.controller;

import com.hrythym.model.Song;
import com.hrythym.model.User;
import com.hrythym.repository.SongRepository;
import com.hrythym.repository.UserRepository;
import com.hrythym.service.LogService;
import com.hrythym.service.action.ActionContext;
import com.hrythym.service.action.PlayTrackAction;
import com.hrythym.service.action.PauseTrackAction;
import com.hrythym.service.player.MediaPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/player")
public class PlaybackController {

    @Autowired private PlayTrackAction  playAction;
    @Autowired private PauseTrackAction pauseAction;
    @Autowired private SongRepository   songRepo;
    @Autowired private UserRepository   userRepo;
    @Autowired private MediaPlayerService mediaPlayerService;
    @Autowired private LogService logService;

    @PostMapping("/play/{songId}")
    public void play(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long songId
    ) {
        User user = userRepo.findByUsername(principal.getUsername()).get();
        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        ActionContext ctx = new ActionContext(user, song, Map.of());
        playAction.execute(ctx);
    }

    @PostMapping("/pause/{songId}")
    public void pause(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long songId
    ) {
        User user = userRepo.findByUsername(principal.getUsername()).get();
        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        ActionContext ctx = new ActionContext(user, song, Map.of());
        pauseAction.execute(ctx);
    }

    @PostMapping("/skip")
    public ResponseEntity<Song> skip(@AuthenticationPrincipal UserDetails principal) {
        Song next = mediaPlayerService.skip();
        if (next != null) {
            User user = userRepo.findByUsername(principal.getUsername()).orElseThrow();
            logService.logAction(user, next, "SKIP");
            return ResponseEntity.ok(next);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/repeat/{songId}")
    public ResponseEntity<String> repeat(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long songId
    ) {
        Song song = songRepo.findById(songId).orElse(null);
        if (song == null) return ResponseEntity.badRequest().body("Song not found");
        mediaPlayerService.repeat(song);
        User user = userRepo.findByUsername(principal.getUsername()).orElseThrow();
        logService.logAction(user, song, "REPEAT");
        return ResponseEntity.ok("Repeated");
    }

}
