package lt.tazkazz.mrqtr.web.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lt.tazkazz.mrqtr.service.ItunesService;
import lt.tazkazz.mrqtr.web.v1.model.ArtistCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/artists")
public class ArtistController {
    @Autowired
    private ItunesService itunesService;

    @Operation(summary = "Search for artists by keyword")
    @GetMapping("/search")
    public ArtistCollection searchArtists(
        @Parameter(description = "Keyword to match")
        @RequestParam String keyword
    ) {
        var artists = itunesService.findArtistsByKeyword(keyword);
        return new ArtistCollection(artists);
    }
}
