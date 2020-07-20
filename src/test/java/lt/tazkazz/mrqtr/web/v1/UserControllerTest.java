package lt.tazkazz.mrqtr.web.v1;

import lt.tazkazz.mrqtr.model.Album;
import lt.tazkazz.mrqtr.repository.UserFavouriteArtistRepository;
import lt.tazkazz.mrqtr.service.ItunesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFavouriteArtistRepository userFavouriteArtistRepository;

    @MockBean
    private ItunesService itunesService;

    @Test
    public void whenRequestedToSaveFavouriteArtistId_shouldStoreItInRepository() throws Exception {
        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{\"artistId\": 1337}"))
            .andExpect(status().isOk());

        verify(userFavouriteArtistRepository).saveForUserId(eq(42), eq(1337));
    }

    @Test
    public void whenInvalidDataReceived_shouldRespondWithBadRequest() throws Exception {
        mockMvc.perform(postJson("/v1/users/INVALID/favourite-artist", "{\"artistId\": 1337}"))
            .andExpect(status().isBadRequest());

        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{\"artistId\": \"INVALID\"}"))
            .andExpect(status().isBadRequest());

        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRequestedToGetTopAlbums_shouldResponseWithThem() throws Exception {
        when(userFavouriteArtistRepository.getByUserId(eq(42)))
            .thenReturn(1337);

        when(itunesService.findArtistTopAlbums(eq(1337), eq(5)))
            .thenReturn(List.of(
                new Album(10, "Artist", "Album 1", "URL 1", "URL 2", new BigDecimal("14.99"), "EUR", 7, LocalDate.parse("2020-05-07"), "Genre")
            ));

        mockMvc.perform(get("/v1/users/42/top-albums"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.count", is(1)))
            .andExpect(jsonPath("$.albums[0].id", is(10)))
            .andExpect(jsonPath("$.albums[0].artistName", is("Artist")))
            .andExpect(jsonPath("$.albums[0].name", is("Album 1")))
            .andExpect(jsonPath("$.albums[0].url", is("URL 1")))
            .andExpect(jsonPath("$.albums[0].artworkUrl", is("URL 2")))
            .andExpect(jsonPath("$.albums[0].price", is(14.99)))
            .andExpect(jsonPath("$.albums[0].currency", is("EUR")))
            .andExpect(jsonPath("$.albums[0].tracks", is(7)))
            .andExpect(jsonPath("$.albums[0].releaseDate", is("2020-05-07")))
            .andExpect(jsonPath("$.albums[0].genre", is("Genre")));
    }

    @Test
    public void whenRequestedToGetTopAlbumsAndUserHasNoFavouriteArtist_shouldRespondWithServerError() throws Exception {
        when(userFavouriteArtistRepository.getByUserId(eq(42)))
            .thenReturn(null);

        mockMvc.perform(get("/v1/users/42/top-albums"))
            .andExpect(status().isInternalServerError());
    }

    private MockHttpServletRequestBuilder postJson(String url, String json) {
        return post(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }
}