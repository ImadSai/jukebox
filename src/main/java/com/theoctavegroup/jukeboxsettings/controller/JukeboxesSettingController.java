package com.theoctavegroup.jukeboxsettings.controller;

import com.theoctavegroup.jukeboxsettings.configuration.SwaggerConfiguration;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Jukebox Settings Controller
 */
@Api(tags = {SwaggerConfiguration.JUKEBOX_SETTING_CONTROLLER})
@RestController
public class JukeboxesSettingController {

    // Jukebox Settings Service
    JukeboxSettingsService jukeboxSettingsService;

    // Inject
    @Autowired
    JukeboxesSettingController(JukeboxSettingsService jukeboxSettingsService) {
        this.jukeboxSettingsService = jukeboxSettingsService;
    }

    /**
     * Endpoint to get all Jukeboxes that fit the SettingId and Model
     *
     * @param settingId : setting id
     * @param model     : filter by model name
     * @param offset    : specifies at what index start the page
     * @param limit     : specifies the page size
     * @return All the Jukeboxes
     */
    @ApiOperation(value = "Endpoint to get all Jukeboxes that fit the SettingId and Model")
    @GetMapping(value = "/getJukeboxes")
    public List<JukeboxDTO> getJukeboxesBySettings(
            @ApiParam(value = "Setting id", required = true, example = "515ef38b-0529-418f-a93a-7f2347fc5805") @RequestParam(name = "settingId") String settingId,
            @ApiParam(value = "Filter by model name", example = "virtuo") @RequestParam(name = "model", required = false) String model,
            @ApiParam(value = "Specifies at what index start the page (Starts from 0)", example = "0") @RequestParam(name = "offset", required = false) Integer offset,
            @ApiParam(value = "Specifies the page size", example = "10") @RequestParam(name = "limit", required = false) Integer limit) {

        return this.jukeboxSettingsService.getJukeboxesBySettings(settingId, model, offset, limit);
    }

}
