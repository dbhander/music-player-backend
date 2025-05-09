package com.hrythym.model;

import java.util.Optional;

public interface PlaylistComponent extends Iterable<PlaylistComponent> {
    void add(PlaylistComponent c);
    void remove(PlaylistComponent c);
    String getName();
    Optional<Song> getSong(); // leaf nodes return a Song

}
