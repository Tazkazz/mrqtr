package lt.tazkazz.mrqtr;

import lt.tazkazz.mrqtr.dto.ArtistDto;
import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.model.Artist;

import java.util.List;

public class DataBuilders {
    public static ArtistSearchResultDto artistSearchResultDto(List<ArtistDto> artists) {
        return new ArtistSearchResultDto() {{
            results = artists;
            resultCount = artists.size();
        }};
    }

    public static ArtistDto artistDto(Integer amgId, String name, String linkUrl, String genreName) {
        return new ArtistDto() {{
            amgArtistId = amgId;
            artistName = name;
            artistLinkUrl = linkUrl;
            primaryGenreName = genreName;
        }};
    }

    public static Artist artist(int id, String name, String url, String genre) {
        return new Artist(id, name, url, genre);
    }
}
