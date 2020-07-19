package lt.tazkazz.mrqtr.web.v1;

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

    @PostMapping("/{userId}/favourite-artist")
    public void saveFavouriteArtist(
        @PathVariable int userId,
        @RequestBody @Valid FavouriteArtistRequest request
    ) {
        userFavouriteArtistRepository.saveForUserId(userId, request.artistId);
    }

    @GetMapping("/{userId}/top-albums")
    public AlbumCollection getTopAlbums(
        @PathVariable int userId,
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
