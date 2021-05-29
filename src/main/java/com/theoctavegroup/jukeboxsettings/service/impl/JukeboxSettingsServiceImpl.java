package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.proxies.JukeboxApi;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Jukebox Settings Service Implementation
 */
@Service
public class JukeboxSettingsServiceImpl implements JukeboxSettingsService {

    JukeboxApi jukeboxApi;

    SettingsService settingsService;


    JukeboxSettingsServiceImpl(JukeboxApi jukeboxApi, SettingsService settingsService) {
        this.jukeboxApi = jukeboxApi;
        this.settingsService = settingsService;
    }

    @Override
    public List<JukeboxDTO> getJukeboxesBySettings(String settingId, String model) {

        // Get Settings By Id
        SettingPropertiesDTO setting = this.settingsService.getSettingById(settingId);



        return Collections.emptyList();
    }
}
