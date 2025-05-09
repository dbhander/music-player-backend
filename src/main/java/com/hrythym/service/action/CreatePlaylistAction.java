package com.hrythym.service.action;

import com.hrythym.model.PlaybackLog;
import com.hrythym.model.Playlist;
import com.hrythym.model.Song;
import com.hrythym.repository.PlaylistRepository;
import com.hrythym.repository.PlaybackLogRepository;
import com.hrythym.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Template Method + Composite/Iterator:
 * 1) logAction: always write a PlaybackLog (including genre/mood)
 * 2) performCoreAction: build + save the playlist
 */
@Component
public class CreatePlaylistAction extends UserMusicAction {

    @Autowired private SongRepository          songRepo;
    @Autowired private PlaylistRepository      playlistRepo;
    @Autowired private PlaybackLogRepository   logRepo;

    @Override
    protected void logAction(ActionContext ctx) {
        // pull filters out of metadata
        String genre = (String)ctx.getMetadata().get("genre");
        String mood  = (String)ctx.getMetadata().get("mood");

        PlaybackLog log = new PlaybackLog();
        log.setUserId(ctx.getUser().getId());
        log.setAction("CREATE_PLAYLIST");
        log.setGenre(genre);
        log.setMood(mood);
        log.setTimestamp(LocalDateTime.now());
        logRepo.save(log);
    }

    @Override
    protected void performCoreAction(ActionContext ctx) {
        String genre = (String)ctx.getMetadata().get("genre");
        String mood  = (String)ctx.getMetadata().get("mood");

        // fetch matching songs
        List<Song> tracks = songRepo.findByMoodAndGenre(mood, genre);

        Playlist p = new Playlist();
        p.setName("Auto: " + genre + "/" + mood);
        p.setOwner(ctx.getUser());
        p.setSongs(tracks);
        playlistRepo.save(p);
    }
}
