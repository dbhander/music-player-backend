package com.hrythym.service.action;

import com.hrythym.model.Song;
import com.hrythym.model.User;
import java.util.Map;

public class ActionContext {
    private final User   user;
    private final Song   song;       // null for playlist actions
    private final Map<String,Object> metadata;

    public ActionContext(User user, Song song, Map<String,Object> metadata) {
        this.user     = user;
        this.song     = song;
        this.metadata = metadata;
    }
    public User getUser()                { return user; }
    public Song getSong()                { return song; }
    public Map<String,Object> getMetadata() { return metadata; }
}
