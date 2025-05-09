package com.hrythym.controller;

import com.hrythym.model.Playlist;
import com.hrythym.model.User;
import com.hrythym.repository.PlaylistRepository;
import com.hrythym.service.action.ActionContext;
import com.hrythym.service.action.CreatePlaylistAction;
import com.hrythym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired private CreatePlaylistAction createAction;
    @Autowired private UserRepository        userRepo;
    @Autowired private PlaylistRepository playlistRepo;

    @PostMapping("/generate")
    public void generate(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody Map<String,String> filters
    ) {
        User user = userRepo.findByUsername(principal.getUsername()).get();
        // filters might contain keys like "genre" or "mood"
        ActionContext ctx = new ActionContext(user, null, Map.copyOf(filters));
        createAction.execute(ctx);
    }

    @GetMapping("/user")
    public List<Playlist> getUserPlaylists(@AuthenticationPrincipal UserDetails principal) {
        User user = userRepo.findByUsername(principal.getUsername()).get();
        return playlistRepo.findByOwner(user);
    }

}
