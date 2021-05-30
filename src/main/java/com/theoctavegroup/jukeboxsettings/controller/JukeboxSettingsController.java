package com.theoctavegroup.jukeboxsettings.controller;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @param model  : filter by model name
     * @param offset : specifies at what index start the page
     * @param limit  : specifies the page size
     * @return All the Jukeboxes
     */
    @GetMapping(value = "/getJukeboxes")
    public List<JukeboxDTO> getJukeboxesBySettings(
            @RequestParam(name = "settingId") String settingId,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "offset", required = false) Integer offset,
            @RequestParam(name = "limit", required = false) Integer limit) {

        return this.jukeboxSettingsService.getJukeboxesBySettings(settingId, model, offset, limit);
    }

}
