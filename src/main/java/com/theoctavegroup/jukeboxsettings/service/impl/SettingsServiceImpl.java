package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.proxies.SettingsApi;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        SettingPropertiesDTO foundSetting = conSettingsMap.get(id);

        if(foundSetting == null) {
            List<SettingPropertiesDTO> listSettingPropertiesDTO = this.settingsApi.getAllSettings();
            conSettingsMap = listSettingPropertiesDTO.stream()
                    .collect(Collectors.toMap(SettingPropertiesDTO::getId, Function.identity()));
            foundSetting = conSettingsMap.get(id);
            if (foundSetting == null) throw new ResourceNotFoundException("Setting Not Found");
        }

        return foundSetting;
    }

}
