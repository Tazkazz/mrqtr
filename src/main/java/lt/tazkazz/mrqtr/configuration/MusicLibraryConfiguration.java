package lt.tazkazz.mrqtr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class MusicLibraryConfiguration {
    @Bean
    public MappingJackson2HttpMessageConverter javascriptToJsonConverter() {
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(new MediaType("text", "javascript")));
        return converter;
    }

    @Bean
    public RestTemplate iTunesRestTemplate(
        @Value("${iTunes.rootUrl}") String rootUrl,
        MappingJackson2HttpMessageConverter javascriptToJsonConverter
    ) {
        return new RestTemplateBuilder()
            .rootUri(rootUrl)
            .messageConverters(javascriptToJsonConverter)
            .build();
    }
}
