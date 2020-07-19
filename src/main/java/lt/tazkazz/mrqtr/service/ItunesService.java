package lt.tazkazz.mrqtr.service;

import lt.tazkazz.mrqtr.dto.ArtistDto;
import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.exception.ItunesException;
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

    private final Logger log = LoggerFactory.getLogger(ItunesService.class);

    public List<Artist> findArtistsByKeyword(String keyword) {
        try {
            log.info("Searching for artists with keyword '{}'", keyword);
            var response = restTemplate.getForObject("/search?entity=allArtist&term={term}", ArtistSearchResultDto.class, keyword);

            if (response != null) {
                log.info("Found {} artists with keyword '{}'", response.resultCount, keyword);
                return toArtistModel(response.results);
            }

            log.info("Search returned null for artists with keyword '{}'", keyword);
            return List.of();
        } catch (RestClientException e) {
            log.error("Search failed for artists with keyword '{}'", keyword, e);
            throw new ItunesException("Artist search failed", e);
        }
    }

    private List<Artist> toArtistModel(List<ArtistDto> artists) {
        return artists.stream()
            .filter(artist -> artist.amgArtistId != null)
            .map(Artist::fromDto)
            .collect(Collectors.toList());
    }
}
