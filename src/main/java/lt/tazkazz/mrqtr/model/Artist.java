package lt.tazkazz.mrqtr.model;

import lt.tazkazz.mrqtr.dto.ArtistDto;

public class Artist {
    public int id;
    public String name;
    public String url;
    public String genre;

    public Artist(int id, String name, String url, String genre) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.genre = genre;
    }

    public static Artist fromDto(ArtistDto dto) {
        return new Artist(
            dto.amgArtistId,
            dto.artistName,
            dto.artistLinkUrl,
            dto.primaryGenreName
        );
    }
}
