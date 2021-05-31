package com.theoctavegroup.jukeboxsettings.api;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.exception.WebClientCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Jukebox API
 */
@Service
public class JukeboxApi {

    // Timeout Duration
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    // Cache Size
    private static final int CACHE_SIZE = 50;

    // Cache Duration
    private static final int CACHE_DURATION = 30;

    // Jukebox Api Client
    private final WebClient jukeboxRestClient;

    // Jukeboxes Cache
    private Cache<String, List<JukeboxDTO>> jukeboxesCache;

    @Autowired
    public JukeboxApi(@Qualifier("jukeboxRestClient") WebClient jukeboxRestClient) {
        this.jukeboxRestClient = jukeboxRestClient;
        this.jukeboxesCache = Caffeine.newBuilder().maximumSize(CACHE_SIZE)
                .expireAfterWrite(Duration.ofSeconds(CACHE_DURATION)).build();
    }

    /**
     * Get All Jukeboxes
     */
    public List<JukeboxDTO> getAllJukeboxesByModel(String model) {

        List<JukeboxDTO> result = this.jukeboxesCache.getIfPresent(model);

        // If the cache is empty we call the Service
        if (result == null) {
            Optional<String> optionalModel = Optional.of(model).filter(s -> !s.isEmpty());

            result = jukeboxRestClient
                    .get()
                    .uri(builder -> builder.path("/").queryParamIfPresent("model", optionalModel).build())
                    .retrieve()
                    .onStatus((HttpStatus::isError), response -> {
                        throw new WebClientCustomException("Error Jukebox Service", response.statusCode());
                    })
                    .bodyToFlux(JukeboxDTO.class)
                    .timeout(REQUEST_TIMEOUT)
                    .collectList()
                    .doOnNext(response -> this.jukeboxesCache.put(model, response))
                    .block();
        }

        return result;
    }

}
