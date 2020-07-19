package lt.tazkazz.mrqtr.web.v1.model;

import javax.validation.constraints.NotNull;

public class FavouriteArtistRequest {
    @NotNull
    public Integer artistId;
}
