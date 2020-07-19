package lt.tazkazz.mrqtr.dto;

import java.util.List;

public class AlbumSearchResultDto {
    public int resultCount;
    public List<AlbumDto> results;

    public AlbumSearchResultDto() {
    }

    public AlbumSearchResultDto(int resultCount, List<AlbumDto> results) {
        this.resultCount = resultCount;
        this.results = results;
    }
}
