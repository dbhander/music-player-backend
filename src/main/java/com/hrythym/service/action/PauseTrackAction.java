package com.hrythym.service.action;

import com.hrythym.service.player.MediaPlayerService;
import com.hrythym.model.PlaybackLog;
import com.hrythym.repository.PlaybackLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PauseTrackAction extends UserMusicAction {

    @Autowired private MediaPlayerService player;
    @Autowired private PlaybackLogRepository logRepo;

    @Override
    protected void logAction(ActionContext ctx) {
        PlaybackLog log = new PlaybackLog();
        log.setUserId(ctx.getUser().getId());
        log.setSongId(ctx.getSong().getId());
        log.setAction("PAUSE");
        log.setGenre(ctx.getSong().getGenre());
        log.setMood(ctx.getSong().getMood());
        log.setTimestamp(LocalDateTime.now());
        logRepo.save(log);
    }

    @Override
    protected void performCoreAction(ActionContext ctx) {
        player.pause();
    }
}
