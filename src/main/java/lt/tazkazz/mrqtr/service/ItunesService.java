package lt.tazkazz.mrqtr.service;

import lt.tazkazz.mrqtr.dto.AlbumDto;
import lt.tazkazz.mrqtr.dto.AlbumSearchResultDto;
import lt.tazkazz.mrqtr.dto.ArtistDto;
import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.exception.ItunesException;
import lt.tazkazz.mrqtr.model.Album;
import lt.tazkazz.mrqtr.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItunesService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String ALBUM_COLLECTION_TYPE = "Album";

    private final Logger log = LoggerFactory.getLogger(ItunesService.class);

    public List<Artist> findArtistsByKeyword(String keyword) {
        try {
            log.info("Searching for artists with keyword '{}'", keyword);
            var response = restTemplate.getForObject("/search?entity=allArtist&term={term}", ArtistSearchResultDto.class, keyword);

            if (response != null) {
                log.info("Found {} artists with keyword '{}'", response.getResultCount(), keyword);
                return toArtistModel(response.getResults());
            }

            log.info("Search returned null for artists with keyword '{}'", keyword);
            return List.of();
        } catch (RestClientException e) {
            log.error("Search failed for artists with keyword '{}'", keyword, e);
            throw new ItunesException("Artist search failed", e);
        }
    }

    public List<Album> findArtistTopAlbums(int artistId, int size) {
        try {
            log.info("Searching for artist's '{}' top {} albums", artistId, size);
            var response = restTemplate.getForObject("/lookup?entity=album&amgArtistId={artistId}&limit={size}", AlbumSearchResultDto.class, artistId, size);

            if (response != null) {
                log.info("Found top albums of artist with id '{}'", artistId);
                return toAlbumModel(response.getResults());
            }

            log.info("Search returned null for top albums of artist with id '{}'", artistId);
            return List.of();
        } catch (RestClientException e) {
            log.error("Search failed for artist with id '{}'", artistId, e);
            throw new ItunesException("Top albums search failed", e);
        }
    }

    private List<Artist> toArtistModel(List<ArtistDto> artists) {
        return artists.stream()
            .filter(artist -> artist.getAmgArtistId() != null)
            .map(Artist::fromDto)
            .collect(Collectors.toList());
    }

    private List<Album> toAlbumModel(List<AlbumDto> albums) {
        return albums.stream()
            .filter(album -> ALBUM_COLLECTION_TYPE.equals(album.getCollectionType()))
            .map(Album::fromDto)
            .collect(Collectors.toList());
    }
}
