package lt.tazkazz.mrqtr.web.v1.model;

import lt.tazkazz.mrqtr.model.Album;

import java.util.List;

public class AlbumCollection {
    public int count;
    public List<Album> albums;

    public AlbumCollection(List<Album> albums) {
        this.count = albums.size();
        this.albums = albums;
    }
}
