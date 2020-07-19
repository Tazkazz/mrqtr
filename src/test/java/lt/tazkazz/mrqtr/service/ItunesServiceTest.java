package lt.tazkazz.mrqtr.service;

import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.exception.ItunesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static lt.tazkazz.mrqtr.DataBuilders.artistDto;
import static lt.tazkazz.mrqtr.DataBuilders.artistSearchResultDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItunesServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ItunesService itunesService;

    @Test
    public void whenArtistsFoundInItunes_shouldReturnMappedArtists() {
        when(restTemplate.getForObject(any(), eq(ArtistSearchResultDto.class), eq("artist")))
            .thenReturn(artistSearchResultDto(List.of(
                artistDto(null, "Unknown artist", "URL", "Genre"),
                artistDto(1337, "Artist", "URL 2", "Cool genre")
            )));

        var result = itunesService.findArtistsByKeyword("artist");

        assertThat(result, notNullValue());
        assertThat(result.size(), is(1));

        var firstArtist = result.get(0);
        assertThat(firstArtist.id, is(1337));
        assertThat(firstArtist.name, is("Artist"));
        assertThat(firstArtist.url, is("URL 2"));
        assertThat(firstArtist.genre, is("Cool genre"));
    }

    @Test
    public void whenNoArtistsFoundInItunes_shouldReturnEmptyList() {
        when(restTemplate.getForObject(any(), eq(ArtistSearchResultDto.class), eq("artist")))
            .thenReturn(artistSearchResultDto(List.of()));

        var result = itunesService.findArtistsByKeyword("artist");

        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

    @Test
    public void whenItunesReturnedNullArtists_shouldReturnEmptyList() {
        when(restTemplate.getForObject(any(), eq(ArtistSearchResultDto.class), eq("artist")))
            .thenReturn(null);

        var result = itunesService.findArtistsByKeyword("artist");

        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

    @Test
    public void whenItunesArtistSearchFailed_shouldThrow() {
        when(restTemplate.getForObject(any(), eq(ArtistSearchResultDto.class), eq("artist")))
            .thenThrow(new RestClientException(""));

        assertThrows(ItunesException.class, () -> itunesService.findArtistsByKeyword("artist"));
    }
}