package com.theoctavegroup.jukeboxsettings.controller;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;

import java.util.List;

/**
 * Jukebox Settings Controller
 */
@RestController
public class JukeboxSettingsController {

    // Jukebox Settings Service
    JukeboxSettingsService jukeboxSettingsService;

    // Inject
    @Autowired
    JukeboxSettingsController(JukeboxSettingsService jukeboxSettingsService) {
        this.jukeboxSettingsService = jukeboxSettingsService;
    }

    /**
     * Endpoint to get all Jukeboxes that fit the SettingId and Model
     * @return All the Jukeboxes
     */
    @GetMapping(value = "/getJukeboxes")
    public List<JukeboxDTO> getJukeboxesBySettings(@RequestParam(name = "settingId") String settingId, @RequestParam(name = "model", required = false) String model) {
        return this.jukeboxSettingsService.getJukeboxesBySettings(settingId, model);
    }

}
