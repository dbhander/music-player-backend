package com.hrythym.service.action;

import com.hrythym.service.LogService;
import com.hrythym.service.player.MediaPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepeatTrackAction extends UserMusicAction {

    @Autowired
    private MediaPlayerService mediaPlayer;
    @Autowired private LogService logger;

    @Override
    protected void logAction(ActionContext ctx) {
        logger.logAction(ctx.getUser(), ctx.getSong(), "REPEAT");
    }

    @Override
    protected void performCoreAction(ActionContext ctx) {
        mediaPlayer.repeat(ctx.getSong());
    }
}
