package com.hrythym.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PlaylistComposite implements PlaylistComponent {
    private final String name;
    private final List<PlaylistComponent> children = new ArrayList<>();
    public PlaylistComposite(String name) { this.name = name; }
    public void add(PlaylistComponent c) { children.add(c); }
    public void remove(PlaylistComponent c) { children.remove(c); }
    public String getName() { return name; }
    public Optional<Song> getSong() { return Optional.empty(); }
    public Iterator<PlaylistComponent> iterator() {
        return children.iterator();
    }
}
