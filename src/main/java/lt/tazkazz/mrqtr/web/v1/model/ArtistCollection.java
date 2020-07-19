package lt.tazkazz.mrqtr.web.v1.model;

import lt.tazkazz.mrqtr.model.Artist;

import java.util.List;

public class ArtistCollection {
    public int count;
    public List<Artist> artists;

    public ArtistCollection(List<Artist> artists) {
        this.count = artists.size();
        this.artists = artists;
    }
}
