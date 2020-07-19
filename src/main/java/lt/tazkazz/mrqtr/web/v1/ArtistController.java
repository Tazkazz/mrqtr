package lt.tazkazz.mrqtr.web.v1;

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

    @GetMapping("/search")
    public ArtistCollection searchArtists(@RequestParam String keyword) {
        var artists = itunesService.findArtistsByKeyword(keyword);
        return new ArtistCollection(artists);
    }
}
