package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.api.SettingsApi;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exception.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Settings Service Implementation
 */
@Service
public class SettingsServiceImpl implements SettingsService {

    // Settings API
    SettingsApi settingsApi;

    // Settings Concurrent HashMap
    Map<String, SettingPropertiesDTO> conSettingsMap = new ConcurrentHashMap<>();

    @Autowired
    SettingsServiceImpl(SettingsApi settingsApi) {
        this.settingsApi = settingsApi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingPropertiesDTO getSettingById(String id) {

        SettingPropertiesDTO settingFound = conSettingsMap.get(id);

        // If the ConcurrentHashMap Doesn't have the settings we are searching, we reload the Data
        if (settingFound == null) {

            // Reloading all the Settings
            conSettingsMap = this.settingsApi.getAllSettings().stream()
                    .collect(Collectors.toMap(SettingPropertiesDTO::getId, Function.identity()));

            // Get Settings by ID
            settingFound = conSettingsMap.get(id);

            // If we still don't find the Setting we throw a Resource Not Found Exception
            if (settingFound == null) throw new ResourceNotFoundException("Setting Not Found");
        }

        return settingFound;
    }

}
