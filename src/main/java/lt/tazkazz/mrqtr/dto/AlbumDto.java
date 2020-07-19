package lt.tazkazz.mrqtr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AlbumDto {
    public String collectionType;
    public Integer collectionId;
    public String artistName;
    public String collectionName;
    public String collectionViewUrl;
    public String artworkUrl100;
    public BigDecimal collectionPrice;
    public String currency;
    public Integer trackCount;
    public LocalDate releaseDate;
    public String primaryGenreName;

    public AlbumDto() {
    }

    public AlbumDto(
        String collectionType,
        Integer collectionId,
        String artistName,
        String collectionName,
        String collectionViewUrl,
        String artworkUrl100,
        BigDecimal collectionPrice,
        String currency,
        Integer trackCount,
        LocalDate releaseDate,
        String primaryGenreName
    ) {
        this.collectionType = collectionType;
        this.collectionId = collectionId;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.collectionViewUrl = collectionViewUrl;
        this.artworkUrl100 = artworkUrl100;
        this.collectionPrice = collectionPrice;
        this.currency = currency;
        this.trackCount = trackCount;
        this.releaseDate = releaseDate;
        this.primaryGenreName = primaryGenreName;
    }
}
