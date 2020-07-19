package lt.tazkazz.mrqtr.service;

import lt.tazkazz.mrqtr.dto.AlbumSearchResultDto;
import lt.tazkazz.mrqtr.dto.ArtistSearchResultDto;
import lt.tazkazz.mrqtr.exception.ItunesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static lt.tazkazz.mrqtr.DataBuilders.*;
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

    @Test
    public void whenTopAlbumsFoundInItunes_shouldReturnMappedAlbums() {
        when(restTemplate.getForObject(any(), eq(AlbumSearchResultDto.class), eq(1337), eq(5)))
            .thenReturn(albumSearchResultDto(List.of(
                albumDto("Artist", 0, "", "", "", "", ZERO, "", 0, null, ""),
                albumDto("Album", 10, "Artist", "Album 1", "URL 1", "URL 2", new BigDecimal("14.99"), "EUR", 7, LocalDate.parse("2020-05-07"), "Genre"),
                albumDto("Album", 20, "Artist", "Album 2", "URL 3", "URL 4", new BigDecimal("8.00"), "GBP", 5, LocalDate.parse("1999-01-23"), "Genre 2")
            )));

        var result = itunesService.findArtistTopAlbums(1337, 5);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(2));

        var firstAlbum = result.get(0);
        assertThat(firstAlbum.id, is(10));
        assertThat(firstAlbum.artistName, is("Artist"));
        assertThat(firstAlbum.name, is("Album 1"));
        assertThat(firstAlbum.url, is("URL 1"));
        assertThat(firstAlbum.artworkUrl, is("URL 2"));
        assertThat(firstAlbum.price, is(new BigDecimal("14.99")));
        assertThat(firstAlbum.currency, is("EUR"));
        assertThat(firstAlbum.tracks, is(7));
        assertThat(firstAlbum.releaseDate, is(LocalDate.parse("2020-05-07")));
        assertThat(firstAlbum.genre, is("Genre"));

        var secondAlbum = result.get(1);
        assertThat(secondAlbum.id, is(20));
        assertThat(secondAlbum.artistName, is("Artist"));
        assertThat(secondAlbum.name, is("Album 2"));
        assertThat(secondAlbum.url, is("URL 3"));
        assertThat(secondAlbum.artworkUrl, is("URL 4"));
        assertThat(secondAlbum.price, is(new BigDecimal("8.00")));
        assertThat(secondAlbum.currency, is("GBP"));
        assertThat(secondAlbum.tracks, is(5));
        assertThat(secondAlbum.releaseDate, is(LocalDate.parse("1999-01-23")));
        assertThat(secondAlbum.genre, is("Genre 2"));
    }

    @Test
    public void whenNoTopAlbumsFoundInItunes_shouldReturnEmptyList() {
        when(restTemplate.getForObject(any(), eq(AlbumSearchResultDto.class), eq(1337), eq(5)))
            .thenReturn(albumSearchResultDto(List.of()));

        var result = itunesService.findArtistTopAlbums(1337, 5);

        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

    @Test
    public void whenItunesReturnedNullTopAlbums_shouldReturnEmptyList() {
        when(restTemplate.getForObject(any(), eq(AlbumSearchResultDto.class), eq(1337), eq(5)))
            .thenReturn(null);

        var result = itunesService.findArtistTopAlbums(1337, 5);

        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

    @Test
    public void whenItunesTopAlbumSearchFailed_shouldThrow() {
        when(restTemplate.getForObject(any(), eq(AlbumSearchResultDto.class), eq(1337), eq(5)))
            .thenThrow(new RestClientException(""));

        assertThrows(ItunesException.class, () -> itunesService.findArtistTopAlbums(1337, 5));
    }
}