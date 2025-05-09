package com.hrythym.service.player;

import com.hrythym.model.Song;

public class PlayingState implements PlayerState {
    private final MediaPlayerService player;

    public PlayingState(MediaPlayerService player) {
        this.player = player;
    }

    @Override
    public void play() {
        Song song = player.getCurrentSong();
        if (song != null) {
            System.out.println("Already playing: " + song.getTitle());
        } else {
            System.out.println("Already in playing state but no song selected.");
        }
    }

    @Override
    public void pause() {
        Song song = player.getCurrentSong();
        System.out.println("Pausing playback: " + (song != null ? song.getTitle() : "No song selected"));
        player.setState(new PausedState(player));
    }

    @Override
    public void stop() {
        Song song = player.getCurrentSong();
        System.out.println("Stopping playback: " + (song != null ? song.getTitle() : "No song selected"));
        player.setState(new StoppedState(player));
    }
}
