package lt.tazkazz.mrqtr.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.tazkazz.mrqtr.dto.AlbumDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private int id;
    private String artistName;
    private String name;
    private String url;
    private String artworkUrl;
    private BigDecimal price;
    private String currency;
    private Integer tracks;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate releaseDate;
    private String genre;

    public static Album fromDto(AlbumDto dto) {
        return new Album(
            dto.getCollectionId(),
            dto.getArtistName(),
            dto.getCollectionName(),
            dto.getCollectionViewUrl(),
            dto.getArtworkUrl100(),
            dto.getCollectionPrice(),
            dto.getCurrency(),
            dto.getTrackCount(),
            dto.getReleaseDate(),
            dto.getPrimaryGenreName()
        );
    }
}
