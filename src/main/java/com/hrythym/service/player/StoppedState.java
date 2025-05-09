package com.hrythym.service.player;

import com.hrythym.model.Song;

public class StoppedState implements PlayerState {
    private final MediaPlayerService player;

    public StoppedState(MediaPlayerService player) {
        this.player = player;
    }

    @Override
    public void play() {
        Song song = player.getCurrentSong();
        if (song != null) {
            System.out.println("Starting playback: " + song.getTitle());
        } else {
            System.out.println("No song selected to play.");
        }
        player.setState(new PlayingState(player));
    }

    @Override
    public void pause() {
        // cannot pause when stopped—no-op
        System.out.println("Cannot pause. No song is playing.");
    }

    @Override
    public void stop() {
        System.out.println("Already stopped.");
    }
}
