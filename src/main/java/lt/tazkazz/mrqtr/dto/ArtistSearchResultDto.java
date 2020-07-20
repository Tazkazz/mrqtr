package lt.tazkazz.mrqtr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSearchResultDto {
    private int resultCount;
    private List<ArtistDto> results;

    public ArtistSearchResultDto(List<ArtistDto> results) {
        this.resultCount = results.size();
        this.results = results;
    }
}
