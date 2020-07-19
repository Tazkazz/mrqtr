package lt.tazkazz.mrqtr.dto;

public class ArtistDto {
    public Integer amgArtistId;
    public String artistName;
    public String artistLinkUrl;
    public String primaryGenreName;

    public ArtistDto() {
    }

    public ArtistDto(Integer amgArtistId, String artistName, String artistLinkUrl, String primaryGenreName) {
        this.amgArtistId = amgArtistId;
        this.artistName = artistName;
        this.artistLinkUrl = artistLinkUrl;
        this.primaryGenreName = primaryGenreName;
    }
}
