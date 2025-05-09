package com.hrythym.service.player;

import com.hrythym.model.Song;

public class PausedState implements PlayerState {
    private final MediaPlayerService player;

    public PausedState(MediaPlayerService player) {
        this.player = player;
    }

    @Override
    public void play() {
        Song song = player.getCurrentSong();
        System.out.println("Resuming playback: " + (song != null ? song.getTitle() : "No song selected"));
        player.setState(new PlayingState(player));
    }

    @Override
    public void pause() {
        // already paused—no-op
        Song song = player.getCurrentSong();
        System.out.println("Already paused: " + (song != null ? song.getTitle() : "No song selected"));
    }

    @Override
    public void stop() {
        Song song = player.getCurrentSong();
        System.out.println("Stopping from paused: " + (song != null ? song.getTitle() : "No song selected"));
        player.setState(new StoppedState(player));
    }
}
