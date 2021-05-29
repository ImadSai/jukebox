package com.theoctavegroup.jukeboxsettings.service;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;

import java.util.List;

/**
 * Jukebox Settings Service Interface
 */
public interface JukeboxSettingsService {

    /**
     * Service to get all Jukeboxes that fit the SettingId and Model
     * @param settingId
     * @param model : filter by model name
     * @param offset : specifies at what index start the page
     * @param limit : specifies the page size
     * @return All the Jukeboxes
     */
    List<JukeboxDTO> getJukeboxesBySettings(String settingId, String model, Integer offset, Integer limit);

    /**
     * Service to get all Jukeboxes By Model
     * @param model : Jukebox model
     * @return All the Jukeboxes
     */
    List<JukeboxDTO> getAllJukeboxesByModel(String model);

}
