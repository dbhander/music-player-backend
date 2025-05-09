package com.hrythym.service.player;

import com.hrythym.model.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaPlayerService {

    private PlayerState currentState;
    private Song currentSong;
    private List<Song> queue = new ArrayList<>();
    private int currentIndex = -1;

    public MediaPlayerService() {
        this.currentState = new StoppedState(this);
    }

    public void setState(PlayerState newState) {
        this.currentState = newState;
    }

    public void play() {
        currentState.play();
    }

    public void pause() {
        currentState.pause();
    }

    public void stop() {
        currentState.stop();
    }

    // Playback control
    public void repeat(Song song) {
        this.currentSong = song;
        this.currentState = new PlayingState(this);
        System.out.println("Repeating song: " + song.getTitle());
    }

    public Song skip() {
        if (queue.isEmpty() || currentIndex + 1 >= queue.size()) {
            currentSong = null;
            setState(new StoppedState(this));
            return null;
        }
        currentIndex++;
        currentSong = queue.get(currentIndex);
        setState(new PlayingState(this));
        System.out.println("Skipped to: " + currentSong.getTitle());
        return currentSong;
    }

    public void loadQueue(List<Song> playlistSongs) {
        this.queue = playlistSongs;
        this.currentIndex = 0;
        if (!queue.isEmpty()) {
            this.currentSong = queue.get(0);
            this.currentState = new PlayingState(this);
        }
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song song) {
        this.currentSong = song;
    }

    public Song peekNextSong() {
        if (queue.isEmpty() || currentIndex + 1 >= queue.size()) return null;
        return queue.get(currentIndex + 1);
    }
}
