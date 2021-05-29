package com.theoctavegroup.jukeboxsettings.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration of Rest Clients
 */
@Configuration
public class RestClientConfiguration {

    @Value("${jukebox.service.url}")
    String jukeboxServiceUrl;

    @Value("${settings.service.url}")
    String settingsServiceUrl;

    /**
     * Create a Jukebox Rest Client Bean
     * @return Bean
     */
    @Bean("jukeboxRestClient")
    public WebClient createJukeboxRestClientBean() {
        return WebClient.builder().baseUrl(jukeboxServiceUrl).build();
    }

    /**
     * Create a Settings Rest Client Bean
     * @return Bean
     */
    @Bean("settingsRestClient")
    public WebClient createSettingsRestClientBean() {
        return WebClient.builder().baseUrl(settingsServiceUrl).build();
    }

}
