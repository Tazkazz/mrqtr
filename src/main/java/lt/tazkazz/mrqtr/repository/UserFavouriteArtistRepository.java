package lt.tazkazz.mrqtr.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserFavouriteArtistRepository {
    private final Map<Integer, Integer> userIdToFavouriteArtistId = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(UserFavouriteArtistRepository.class);

    public void saveForUserId(int userId, int artistId) {
        userIdToFavouriteArtistId.put(userId, artistId);
        log.info("Stored user's '{}' favourite artist '{}'", userId, artistId);
    }

    public Integer getByUserId(int userId) {
        var artistId = userIdToFavouriteArtistId.get(userId);
        if (artistId != null) {
            log.info("Retrieved user's '{}' favourite artist '{}'", userId, artistId);
        } else {
            log.info("User '{}' has no favourite artist", userId);
        }
        return artistId;
    }
}
