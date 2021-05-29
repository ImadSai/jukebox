package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;

import java.util.List;

/**
 * Jukebox Settings Service Interface
 */
public interface JukeboxSettingsService {

    /**
     * Service to get all Jukeboxes that fit the SettingId and Model
     * @return All the Jukeboxes
     */
    List<JukeboxDTO> getJukeboxesBySettings(String settingId, String model);

    /**
     * Service to get all Jukeboxes By Model
     * @return All the Jukeboxes
     */
    List<JukeboxDTO> getAllJukeboxesByModel(String model);

}
