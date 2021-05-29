package com.theoctavegroup.jukeboxsettings.proxies;

import com.theoctavegroup.jukeboxsettings.dto.SettingDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.WebClientCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Settings API
 */
@Service
public class SettingsApi {

    // Timeout Duration
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    // Jukebox Api Client
    private final WebClient settingsRestClient;

    @Autowired
    public SettingsApi(@Qualifier("settingsRestClient") WebClient settingsRestClient) {
        this.settingsRestClient = settingsRestClient;
    }

    /**
     * Get All Jukeboxes
     */
    public List<SettingPropertiesDTO> getAllSettings() {
        SettingDTO setting = settingsRestClient
                .get()
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new WebClientCustomException("Error Settings Service", response.statusCode())))
                .bodyToMono(SettingDTO.class)
                .timeout(REQUEST_TIMEOUT)
                .block();

        assert setting != null;
        return setting.getSettings() == null ? Collections.emptyList() : setting.getSettings();
    }


}
