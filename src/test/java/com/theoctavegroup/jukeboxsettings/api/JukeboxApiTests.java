package com.theoctavegroup.jukeboxsettings.api;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.exception.WebClientCustomException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class to test Jukebox API (Webflux)
 */
class JukeboxApiTests {

    MockWebServer mockWebServer;

    WebClient jukeboxRestClient;

    JukeboxApi jukeboxApi;

    @BeforeEach
    public void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        jukeboxRestClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
        jukeboxApi = new JukeboxApi(jukeboxRestClient);
    }

    @Test
    void getAllJukeboxesByModel() {

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\"id\": \"123\", \"model\": \"fusion\",\"components\": [{\"name\": \"amplifier\"}] }");

        mockWebServer.enqueue(mockResponse);

        List<JukeboxDTO> result = jukeboxApi.getAllJukeboxesByModel("");

        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getId());
        assertEquals("fusion", result.get(0).getModel());
        assertEquals(1, result.get(0).getComponents().size());
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
            jukeboxApi.getAllJukeboxesByModel("");
        });

        String expectedMessage = "Error Jukebox Service";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }
}
