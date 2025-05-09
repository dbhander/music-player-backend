package com.hrythym.service.action;

import com.hrythym.model.Song;
import com.hrythym.service.LogService;
import com.hrythym.service.player.MediaPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkipTrackAction extends UserMusicAction {

    @Autowired
    private MediaPlayerService mediaPlayer;
    @Autowired private LogService logger;

    @Override
    protected void logAction(ActionContext ctx) {
        Song next = mediaPlayer.peekNextSong(); // this may be implemented or return null
        if (next != null) {
            logger.logAction(ctx.getUser(), next, "SKIP");
        }
    }

    @Override
    protected void performCoreAction(ActionContext ctx) {
        mediaPlayer.skip(); // updates internal state
    }
}
