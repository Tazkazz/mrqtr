package lt.tazkazz.mrqtr.web.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lt.tazkazz.mrqtr.exception.FavouriteArtistNotFoundException;
import lt.tazkazz.mrqtr.repository.UserFavouriteArtistRepository;
import lt.tazkazz.mrqtr.service.ItunesService;
import lt.tazkazz.mrqtr.web.v1.model.AlbumCollection;
import lt.tazkazz.mrqtr.web.v1.model.FavouriteArtistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserFavouriteArtistRepository userFavouriteArtistRepository;

    @Autowired
    private ItunesService itunesService;

    @Operation(summary = "Save user's favourite artist")
    @PostMapping("/{userId}/favourite-artist")
    public void saveFavouriteArtist(
        @Parameter(description = "User ID")
        @PathVariable int userId,

        @RequestBody @Valid FavouriteArtistRequest request
    ) {
        userFavouriteArtistRepository.saveForUserId(userId, request.artistId);
    }

    @Operation(summary = "Get user's favourite artist's top albums")
    @GetMapping("/{userId}/top-albums")
    public AlbumCollection getTopAlbums(
        @Parameter(description = "User ID")
        @PathVariable int userId,

        @Parameter(description = "Number of albums")
        @RequestParam(required = false, defaultValue = "5") int size
    ) {
        var artistId = userFavouriteArtistRepository.getByUserId(userId);

        if (artistId == null) {
            throw new FavouriteArtistNotFoundException("User does not have a favourite artist");
        }

        var albums = itunesService.findArtistTopAlbums(artistId, size);
        return new AlbumCollection(albums);
    }
}
