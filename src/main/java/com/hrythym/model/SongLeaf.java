package com.hrythym.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

public class SongLeaf implements PlaylistComponent {
    private final Song song;
    public SongLeaf(Song song) { this.song = song; }
    public void add(PlaylistComponent c) { throw new UnsupportedOperationException(); }
    public void remove(PlaylistComponent c) { throw new UnsupportedOperationException(); }
    public String getName() { return song.getTitle(); }
    public Optional<Song> getSong() { return Optional.of(song); }
    public Iterator<PlaylistComponent> iterator() {
        return Collections.<PlaylistComponent>singletonList(this).iterator();
    }
}
