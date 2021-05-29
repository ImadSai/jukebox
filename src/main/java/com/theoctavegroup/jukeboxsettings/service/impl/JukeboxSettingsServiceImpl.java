package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.dto.JukeboxComponentDTO;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.proxies.JukeboxApi;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Jukebox Settings Service Implementation
 */
@Service
public class JukeboxSettingsServiceImpl implements JukeboxSettingsService {

    // Jukebox API
    JukeboxApi jukeboxApi;

    // Settings Service
    SettingsService settingsService;

    @Autowired
    JukeboxSettingsServiceImpl(JukeboxApi jukeboxApi, SettingsService settingsService) {
        this.jukeboxApi = jukeboxApi;
        this.settingsService = settingsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JukeboxDTO> getJukeboxesBySettings(String settingId, String model) {

        // Get Settings By Id
        SettingPropertiesDTO setting = this.settingsService.getSettingById(settingId);

        // Get Jukeboxes
        List<JukeboxDTO> jukeboxes = this.getAllJukeboxesByModel(model);

        // Set Empty Result
        List<JukeboxDTO> result = new ArrayList();

        // Create Predicate To Filter Results
        Predicate<JukeboxDTO> checkIfHaveRequiredComponents = jukebox -> {
            // If Jukebox have less number of components than Settings Required
            // if (jukebox.getComponents().size() < setting.getRequires().size()) return false;

            // Check
            for(var requiredComponent : setting.getRequires()) {
                boolean requiredComponentPresent = jukebox.getComponents().stream().anyMatch(o -> o.getName().equals(requiredComponent));
                if(!requiredComponentPresent) return false;
            }
            return true;
        };

        // Get filter Results
        result = jukeboxes.stream()
                .filter(checkIfHaveRequiredComponents)
                .collect(Collectors.toList());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public List<JukeboxDTO> getAllJukeboxesByModel(String model) {
        // Get Jukeboxes
        List<JukeboxDTO> jukeboxes = this.jukeboxApi.getAllJukeboxesByModel(model);

        // Throw error if No Juckboxes or Model Not found
        if(jukeboxes.isEmpty() && (model == null || model.isEmpty()))  throw new ResourceNotFoundException("No Jukebox Found");
        if(jukeboxes.isEmpty())  throw new ResourceNotFoundException("No Jukebox Found with the model specified");

        return jukeboxes;
    }
}
