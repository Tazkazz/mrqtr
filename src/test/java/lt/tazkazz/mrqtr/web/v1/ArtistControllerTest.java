package lt.tazkazz.mrqtr.web.v1;

import lt.tazkazz.mrqtr.exception.ItunesException;
import lt.tazkazz.mrqtr.model.Artist;
import lt.tazkazz.mrqtr.service.ItunesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ArtistController.class})
class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItunesService itunesService;

    @Test
    public void whenArtistsFoundInItunes_shouldRespondWithMappedArtists() throws Exception {
        when(itunesService.findArtistsByKeyword(eq("artist")))
            .thenReturn(List.of(
                new Artist(1337, "Artist", "URL", "Cool genre")
            ));

        mockMvc.perform(get("/v1/artists/search?keyword=artist"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.count", is(1)))
            .andExpect(jsonPath("$.artists[0].id", is(1337)))
            .andExpect(jsonPath("$.artists[0].name", is("Artist")))
            .andExpect(jsonPath("$.artists[0].url", is("URL")))
            .andExpect(jsonPath("$.artists[0].genre", is("Cool genre")));
    }

    @Test
    public void whenNoArtistsFoundInItunes_shouldRespondWithEmptyCollection() throws Exception {
        when(itunesService.findArtistsByKeyword(eq("artist")))
            .thenReturn(List.of());

        mockMvc.perform(get("/v1/artists/search?keyword=artist"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.count", is(0)));
    }

    @Test
    public void whenItunesExceptionIsThrownOnArtistSearch_shouldRespondWithServerError() throws Exception {
        when(itunesService.findArtistsByKeyword(eq("artist")))
            .thenThrow(new ItunesException("", null));

        mockMvc.perform(get("/v1/artists/search?keyword=artist"))
            .andExpect(status().isInternalServerError());
    }
}