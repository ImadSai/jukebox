package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;

/**
 * Jukebox Settings Service Interface
 */
public interface SettingsService {

    /**
     * Get Settigs By ID
     * @param id
     * @return
     */
    SettingPropertiesDTO getSettingById(String id);


}
