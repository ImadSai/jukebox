package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.api.SettingsApi;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Settings Service Implementation
 */
@Service
public class SettingsServiceImpl implements SettingsService {

    // Settings API
    SettingsApi settingsApi;

    @Autowired
    SettingsServiceImpl(SettingsApi settingsApi) {
        this.settingsApi = settingsApi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingPropertiesDTO getSettingById(String id) {
        return this.settingsApi.getSettingsById(id);
    }
}
