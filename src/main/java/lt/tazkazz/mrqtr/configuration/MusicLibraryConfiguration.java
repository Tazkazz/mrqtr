package lt.tazkazz.mrqtr.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
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

    @Bean
    public Caffeine<Object, Object> caffeine(
        @Value("${iTunes.cache.ttlSeconds}") int ttlSeconds,
        @Value("${iTunes.cache.maximumSize}") int maximumSize
    ) {
        return Caffeine.newBuilder()
            .expireAfterWrite(ttlSeconds, TimeUnit.SECONDS)
            .maximumSize(maximumSize);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
