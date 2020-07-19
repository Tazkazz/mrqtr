package lt.tazkazz.mrqtr.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(MockitoExtension.class)
class UserFavouriteArtistRepositoryTest {
    @InjectMocks
    private UserFavouriteArtistRepository userFavouriteArtistRepository;

    @Test
    public void whenSavedFavouriteArtist_shouldReturnTheSame() {
        userFavouriteArtistRepository.saveForUserId(42, 1337);

        var favourite = userFavouriteArtistRepository.getByUserId(42);

        assertThat(favourite, is(1337));
    }

    @Test
    public void whenNoArtistWasSaved_shouldReturnNull() {
        var favourite = userFavouriteArtistRepository.getByUserId(42);

        assertThat(favourite, nullValue());
    }
}