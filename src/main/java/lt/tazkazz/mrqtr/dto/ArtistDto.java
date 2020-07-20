package lt.tazkazz.mrqtr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {
    private Integer amgArtistId;
    private String artistName;
    private String artistLinkUrl;
    private String primaryGenreName;
}
