package com.theoctavegroup.jukeboxsettings.api;

import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exception.WebClientCustomException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class to test Settings API (Webflux)
 */
class SettingsApiTests {

    MockWebServer mockWebServer;

    WebClient settingsRestClient;

    SettingsApi settingsApi;

    @BeforeEach
    public void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        settingsRestClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
        settingsApi = new SettingsApi(settingsRestClient);
    }

    @Test
    void getAllSettingsTest() {

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\"settings\": [ { \"id\": \"1\", \"requires\": [ \"camera\", \"speaker\" ] } ] }");

        mockWebServer.enqueue(mockResponse);

        SettingPropertiesDTO result = settingsApi.getSettingsById("1");

        assertEquals("1", result.getId());
        assertEquals(2, result.getRequires().size());
    }

    /**
     * getAllSettings Fail Test ( Target Server Down )
     */
    @Test
    void getAllSettingsFailServiceDownTest() {

        MockResponse mockResponse = new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("");

        mockWebServer.enqueue(mockResponse);

        Exception exception = assertThrows(WebClientCustomException.class, () -> {
            settingsApi.getSettingsById("1");
        });

        String expectedMessage = "Error Settings Service";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

}
