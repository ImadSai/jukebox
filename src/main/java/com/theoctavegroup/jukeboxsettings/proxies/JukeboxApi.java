package com.theoctavegroup.jukeboxsettings.proxies;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.WebClientCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * Jukebox API
 */
@Service
public class JukeboxApi {

    // Timeout Duration
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    // Jukebox Api Client
    private final WebClient jukeboxRestClient;

    @Autowired
    public JukeboxApi(@Qualifier("jukeboxRestClient") WebClient jukeboxRestClient) {
        this.jukeboxRestClient = jukeboxRestClient;
    }

    /**
     * Get All Jukeboxes
     */
    public List<JukeboxDTO> getAllJukeboxes() {
        return jukeboxRestClient
                .get()
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new WebClientCustomException("Error Jukebox Service", response.statusCode())))
                .bodyToFlux(JukeboxDTO.class)
                .timeout(REQUEST_TIMEOUT)
                .collectList()
                .block();
    }

}
