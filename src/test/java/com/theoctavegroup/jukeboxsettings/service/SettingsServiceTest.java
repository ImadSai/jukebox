package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.api.SettingsApi;
import com.theoctavegroup.jukeboxsettings.service.impl.SettingsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @InjectMocks
    SettingsServiceImpl settingsService;

    @Mock
    SettingsApi settingsApi;

    /**
     * Test getSettingById
     */
    @Test
    void getSettingByIdTest() {

        when(settingsApi.getAllSettings()).thenReturn(this.createSettings());

        SettingPropertiesDTO result = this.settingsService.getSettingById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(3, result.getRequires().size());
    }

    /**
     * Test getSettingById Fail
     */
    @Test
    void getSettingByIdFailTest() {

        when(settingsApi.getAllSettings()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.settingsService.getSettingById("1");
        });

        String expectedMessage = "Setting Not Found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    /**
     * Create Settings
     * @return Settings List
     */
    private List<SettingPropertiesDTO> createSettings() {

        SettingPropertiesDTO s1 = new SettingPropertiesDTO("1", List.of("camera", "speaker", "pcb"));
        SettingPropertiesDTO s2 = new SettingPropertiesDTO("2", List.of("touchscreen", "money_pcb", "led_panel", "money_receiver"));
        SettingPropertiesDTO s3 = new SettingPropertiesDTO("3", List.of("touchscreen", "money_pcb"));

        return List.of(s1, s2, s3);
    }


}
