package com.theoctavegroup.jukeboxsettings.api;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingWrapperDTO;
import com.theoctavegroup.jukeboxsettings.exception.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.exception.WebClientCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Settings API
 */
@Service
public class SettingsApi {

    // Timeout Duration
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    // Cache Size
    private static final int CACHE_SIZE = 50;

    // Cache Duration
    private static final int CACHE_DURATION = 30;

    // Jukebox Api Client
    private final WebClient settingsRestClient;

    // Jukeboxes Cache
    private Cache<String, SettingPropertiesDTO> settingsCache;

    @Autowired
    public SettingsApi(@Qualifier("settingsRestClient") WebClient settingsRestClient) {
        this.settingsRestClient = settingsRestClient;
        this.settingsCache = Caffeine.newBuilder().maximumSize(CACHE_SIZE)
                .expireAfterWrite(Duration.ofSeconds(CACHE_DURATION)).build();
    }

    /**
     * Get Settings by id
     */
    public SettingPropertiesDTO getSettingsById(String id) {
        SettingPropertiesDTO result = this.settingsCache.getIfPresent(id);

        // Reload the cache if the setting not found
        if (result == null) {
            saveAllSettingsInCacheMemory();
            result = this.settingsCache.getIfPresent(id);

            // If the setting still not found we throw an error
            if (result == null) throw new ResourceNotFoundException("Setting Id Not Found");
        }

        return result;
    }

    /**
     * Get All Jukeboxes
     */
    private void saveAllSettingsInCacheMemory() {

        SettingWrapperDTO result = settingsRestClient
                .get()
                .retrieve()
                .onStatus((HttpStatus::isError), response -> {
                    throw new WebClientCustomException("Error Settings Service", response.statusCode());
                })
                .bodyToMono(SettingWrapperDTO.class)
                .timeout(REQUEST_TIMEOUT)
                .block();

        // If no settings found we throw an error
        if (result == null || result.getSettings() == null) throw new ResourceNotFoundException("Settings Not Found");

        // Save the Settings in Cache
        result.getSettings().forEach(setting -> this.settingsCache.put(setting.getId(), setting));
    }
}
