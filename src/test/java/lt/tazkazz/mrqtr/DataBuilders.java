package lt.tazkazz.mrqtr;

import lt.tazkazz.mrqtr.dto.AlbumDto;
import lt.tazkazz.mrqtr.dto.AlbumSearchResultDto;
import lt.tazkazz.mrqtr.dto.ArtistDto;
import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.model.Album;
import lt.tazkazz.mrqtr.model.Artist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DataBuilders {
    public static ArtistSearchResultDto artistSearchResultDto(List<ArtistDto> artists) {
        return new ArtistSearchResultDto(artists.size(), artists);
    }

    public static ArtistDto artistDto(Integer amgId, String name, String linkUrl, String genreName) {
        return new ArtistDto(amgId, name, linkUrl, genreName);
    }

    public static Artist artist(int id, String name, String url, String genre) {
        return new Artist(id, name, url, genre);
    }

    public static AlbumSearchResultDto albumSearchResultDto(List<AlbumDto> albums) {
        return new AlbumSearchResultDto(albums.size(), albums);
    }

    public static AlbumDto albumDto(
        String type,
        int id,
        String artistName,
        String name,
        String url,
        String artworkUrl,
        BigDecimal price,
        String currency,
        Integer trackCount,
        LocalDate releaseDate,
        String genre
    ) {
        return new AlbumDto(type, id, artistName, name, url, artworkUrl, price, currency, trackCount, releaseDate, genre);
    }

    public static Album album(
        int id,
        String artistName,
        String name,
        String url,
        String artworkUrl,
        BigDecimal price,
        String currency,
        Integer trackCount,
        LocalDate releaseDate,
        String genre
    ) {
        return new Album(id, artistName, name, url, artworkUrl, price, currency, trackCount, releaseDate, genre);
    }
}
