package com.theoctavegroup.jukeboxsettings.controller;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxComponentDTO;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(controllers = JukeboxSettingsController.class)
class JukeboxSettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JukeboxSettingsService jukeboxSettingsService;

    @Test
    void getJukeboxesBySettingsTest() throws Exception {

        given(this.jukeboxSettingsService.getJukeboxesBySettings("1", "fusion", 1, 10))
                .willReturn(this.createJukeboxes());

        this.mockMvc.perform(get("/getJukeboxes")
                .param("settingId", "1")
                .param("model", "fusion")
                .param("offset", "1")
                .param("limit", "10")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(this.createJukeboxes().size())));
    }

    /**
     * Create Jukeboxes
     * @return Jukeboxes List
     */
    private List<JukeboxDTO> createJukeboxes() {

        JukeboxComponentDTO jc = new JukeboxComponentDTO("led_panel");
        JukeboxDTO j1 = new JukeboxDTO("1", "fusion", List.of(jc));
        JukeboxDTO j2 = new JukeboxDTO("2", "fusion", List.of(jc));

        return List.of(j1, j2);
    }

}
