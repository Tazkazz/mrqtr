package lt.tazkazz.mrqtr.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.tazkazz.mrqtr.dto.AlbumDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Album {
    public int id;
    public String artistName;
    public String name;
    public String url;
    public String artworkUrl;
    public BigDecimal price;
    public String currency;
    public Integer tracks;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public LocalDate releaseDate;
    public String genre;

    public Album(
        int id,
        String artistName,
        String name,
        String url,
        String artworkUrl,
        BigDecimal price,
        String currency,
        Integer tracks,
        LocalDate releaseDate,
        String genre
    ) {
        this.id = id;
        this.artistName = artistName;
        this.name = name;
        this.url = url;
        this.artworkUrl = artworkUrl;
        this.price = price;
        this.currency = currency;
        this.tracks = tracks;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    public static Album fromDto(AlbumDto dto) {
        return new Album(
            dto.collectionId,
            dto.artistName,
            dto.collectionName,
            dto.collectionViewUrl,
            dto.artworkUrl100,
            dto.collectionPrice,
            dto.currency,
            dto.trackCount,
            dto.releaseDate,
            dto.primaryGenreName
        );
    }
}
