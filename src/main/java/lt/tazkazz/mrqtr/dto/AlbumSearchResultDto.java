package lt.tazkazz.mrqtr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSearchResultDto {
    private int resultCount;
    private List<AlbumDto> results;

    public AlbumSearchResultDto(List<AlbumDto> results) {
        this.resultCount = results.size();
        this.results = results;
    }
}
