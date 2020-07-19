package lt.tazkazz.mrqtr.web.v1;

import lt.tazkazz.mrqtr.repository.UserFavouriteArtistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFavouriteArtistRepository userFavouriteArtistRepository;

    @Test
    public void whenRequestedToSaveFavouriteArtistId_shouldStoreItInRepository() throws Exception {
        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{\"artistId\": 1337}"))
            .andExpect(status().isOk());

        verify(userFavouriteArtistRepository).saveForUserId(eq(42), eq(1337));
    }

    @Test
    public void whenInvalidDataReceived_shouldRespondWith400() throws Exception {
        mockMvc.perform(postJson("/v1/users/INVALID/favourite-artist", "{\"artistId\": 1337}"))
            .andExpect(status().isBadRequest());

        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{\"artistId\": \"INVALID\"}"))
            .andExpect(status().isBadRequest());

        mockMvc.perform(postJson("/v1/users/42/favourite-artist", "{}"))
            .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder postJson(String url, String json) {
        return post(url).contentType(MediaType.APPLICATION_JSON).content(json);
    }
}