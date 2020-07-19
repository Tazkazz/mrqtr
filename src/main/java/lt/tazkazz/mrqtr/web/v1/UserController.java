package lt.tazkazz.mrqtr.web.v1;

import lt.tazkazz.mrqtr.repository.UserFavouriteArtistRepository;
import lt.tazkazz.mrqtr.web.v1.model.FavouriteArtistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserFavouriteArtistRepository userFavouriteArtistRepository;

    @PostMapping("/{userId}/favourite-artist")
    public void saveFavouriteArtist(
        @PathVariable int userId,
        @RequestBody @Valid FavouriteArtistRequest request
    ) {
        userFavouriteArtistRepository.saveForUserId(userId, request.artistId);
    }
}
