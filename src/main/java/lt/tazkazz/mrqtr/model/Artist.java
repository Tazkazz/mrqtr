package lt.tazkazz.mrqtr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.tazkazz.mrqtr.dto.ArtistDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private int id;
    private String name;
    private String url;
    private String genre;

    public static Artist fromDto(ArtistDto dto) {
        return new Artist(
            dto.getAmgArtistId(),
            dto.getArtistName(),
            dto.getArtistLinkUrl(),
            dto.getPrimaryGenreName()
        );
    }
}
