package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.api.JukeboxApi;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxComponentDTO;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exception.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.service.impl.JukeboxSettingsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JukeboxSettingsServiceTests {

    @InjectMocks
    JukeboxSettingsServiceImpl jukeboxSettingsService;

    @Mock
    JukeboxApi jukeboxApi;

    @Mock
    SettingsService settingsService;

    @Test
    void getJukeboxesBySettingsTest() {
        // Init Jukeboxes
        when(this.jukeboxApi.getAllJukeboxesByModel(any(String.class))).thenReturn(this.createJukeboxes());

        // Init Setting
        SettingPropertiesDTO setting = new SettingPropertiesDTO("1", List.of("amplifier"));
        when(this.settingsService.getSettingById(any(String.class))).thenReturn(setting);

        List<JukeboxDTO> result = this.jukeboxSettingsService.getJukeboxesBySettings("1", "", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2", result.get(0).getId());
        assertEquals("angelina", result.get(0).getModel());
    }

    @Test
    void getAllJukeboxesByModelTest() {
        // Init Jukeboxes
        when(this.jukeboxApi.getAllJukeboxesByModel(any(String.class))).thenReturn(this.createJukeboxes());

        List<JukeboxDTO> result = this.jukeboxSettingsService.getAllJukeboxesByModel("");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
    }

    /**
     * getAllJukeboxesByModel Expect Fail ( Empty Database )
     */
    @Test
    void getAllJukeboxesByModelNullFailTest() {
        // Init Jukeboxes
        when(this.jukeboxApi.getAllJukeboxesByModel(null)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.jukeboxSettingsService.getAllJukeboxesByModel(null);
        });

        String expectedMessage = "No Jukebox Found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * getAllJukeboxesByModel Expect Fail ( Model Empty and result Empty )
     */
    @Test
    void getAllJukeboxesByModelEmptyFailTest() {
        // Init Jukeboxes
        when(this.jukeboxApi.getAllJukeboxesByModel("")).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.jukeboxSettingsService.getAllJukeboxesByModel("");
        });

        String expectedMessage = "No Jukebox Found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * getAllJukeboxesByModel Expect Fail ( Model Not Found )
     */
    @Test
    void getAllJukeboxesByModelNotFoundFailTest() {
        // Init Jukeboxes
        when(this.jukeboxApi.getAllJukeboxesByModel(any(String.class))).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.jukeboxSettingsService.getAllJukeboxesByModel("123");
        });

        String expectedMessage = "No Jukebox Found with the model specified";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Create Jukeboxes
     *
     * @return Jukeboxes List
     */
    private List<JukeboxDTO> createJukeboxes() {

        JukeboxComponentDTO jc1 = new JukeboxComponentDTO("led_panel");
        JukeboxDTO j1 = new JukeboxDTO("1", "fusion", List.of(jc1));

        JukeboxComponentDTO jc2 = new JukeboxComponentDTO("amplifier");
        JukeboxDTO j2 = new JukeboxDTO("2", "angelina", List.of(jc2));

        return List.of(j1, j2);
    }

}
