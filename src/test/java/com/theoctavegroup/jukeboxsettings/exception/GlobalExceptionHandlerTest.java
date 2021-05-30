package com.theoctavegroup.jukeboxsettings.exception;

import com.theoctavegroup.jukeboxsettings.controller.JukeboxesSettingController;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JukeboxesSettingController.class)
class GlobalExceptionHandlerTest {

    @MockBean
    JukeboxSettingsService jukeboxSettingsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void ResourceNotFoundExceptionTest() throws Exception {

        given(this.jukeboxSettingsService.getJukeboxesBySettings("1", "fusion", 1, 10))
                .willThrow(new ResourceNotFoundException("No Jukebox found"));

        this.mockMvc.perform(get("/getJukeboxes")
                .param("settingId", "1")
                .param("model", "fusion")
                .param("offset", "1")
                .param("limit", "10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleWebClientCustomExceptionTest() throws Exception {

        given(this.jukeboxSettingsService.getJukeboxesBySettings("1", "fusion", 1, 10))
                .willThrow(new WebClientCustomException("Error", HttpStatus.INTERNAL_SERVER_ERROR));

        this.mockMvc.perform(get("/getJukeboxes")
                .param("settingId", "1")
                .param("model", "fusion")
                .param("offset", "1")
                .param("limit", "10"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void handleWebClientRequestExceptionTest() throws Exception {

        given(this.jukeboxSettingsService.getJukeboxesBySettings("1", "fusion", 1, 10))
                .willThrow(new WebClientRequestException(new Exception("error"), HttpMethod.GET, URI.create(""), HttpHeaders.EMPTY));

        this.mockMvc.perform(get("/getJukeboxes")
                .param("settingId", "1")
                .param("model", "fusion")
                .param("offset", "1")
                .param("limit", "10"))
                .andExpect(status().isInternalServerError());
    }

}
