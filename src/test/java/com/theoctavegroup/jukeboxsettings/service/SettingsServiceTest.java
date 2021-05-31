package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.api.SettingsApi;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.service.impl.SettingsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        when(settingsApi.getSettingsById(any(String.class))).thenReturn(this.createSetting());

        SettingPropertiesDTO result = this.settingsService.getSettingById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(3, result.getRequires().size());
    }

    /**
     * Create Settings
     *
     * @return Settings List
     */
    private SettingPropertiesDTO createSetting() {

        return new SettingPropertiesDTO("1", List.of("camera", "speaker", "pcb"));
    }


}
