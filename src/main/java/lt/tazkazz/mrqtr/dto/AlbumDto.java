package lt.tazkazz.mrqtr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    private String collectionType;
    private Integer collectionId;
    private String artistName;
    private String collectionName;
    private String collectionViewUrl;
    private String artworkUrl100;
    private BigDecimal collectionPrice;
    private String currency;
    private Integer trackCount;
    private LocalDate releaseDate;
    private String primaryGenreName;
}
