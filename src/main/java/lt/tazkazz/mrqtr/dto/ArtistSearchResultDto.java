package lt.tazkazz.mrqtr.dto;

import java.util.List;

public class ArtistSearchResultDto {
    public int resultCount;
    public List<ArtistDto> results;

    public ArtistSearchResultDto() {
    }

    public ArtistSearchResultDto(int resultCount, List<ArtistDto> results) {
        this.resultCount = resultCount;
        this.results = results;
    }
}
