package com.theoctavegroup.jukeboxsettings.service.impl;

import com.theoctavegroup.jukeboxsettings.api.JukeboxApi;
import com.theoctavegroup.jukeboxsettings.dto.JukeboxDTO;
import com.theoctavegroup.jukeboxsettings.dto.SettingPropertiesDTO;
import com.theoctavegroup.jukeboxsettings.exceptions.ResourceNotFoundException;
import com.theoctavegroup.jukeboxsettings.service.JukeboxSettingsService;
import com.theoctavegroup.jukeboxsettings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<JukeboxDTO> getJukeboxesBySettings(String settingId, String model, Integer offset, Integer limit) {

        // Get Settings By Id
        SettingPropertiesDTO setting = this.settingsService.getSettingById(settingId);

        // Get Jukeboxes
        List<JukeboxDTO> jukeboxes = this.getAllJukeboxesByModel(model);

        // Get Results filtered
        List<JukeboxDTO> jukeboxesFiltered = jukeboxes.stream()
                .filter(this.createPredicateToFilterJukeboxes(setting))
                .collect(Collectors.toList());

        return paginatedResult(jukeboxesFiltered, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    public List<JukeboxDTO> getAllJukeboxesByModel(String model) {
        // Get Jukeboxes
        List<JukeboxDTO> jukeboxes = this.jukeboxApi.getAllJukeboxesByModel(model);

        // Throw Resource Not Found Exception if No Jukeboxes or Model Not found
        if (jukeboxes.isEmpty() && (model == null || model.isEmpty()))
            throw new ResourceNotFoundException("No Jukebox Found");
        if (jukeboxes.isEmpty()) throw new ResourceNotFoundException("No Jukebox Found with the model specified");

        return jukeboxes;
    }

    /**
     * Return Predicate To Filter Results witch fit Settings requirements
     *
     * @param setting : Setting Properties
     * @return JukeboxDTO Predicate
     */
    private Predicate<JukeboxDTO> createPredicateToFilterJukeboxes(SettingPropertiesDTO setting) {

        return jukebox -> {
            // If there are no Settings required
            if (setting.getRequires().isEmpty()) return true;

            // If Jukebox have less number of components than Settings Required
            if (jukebox.getComponents().size() < setting.getRequires().size()) return false;

            // Check for every Required Component
            for (var requiredComponent : setting.getRequires()) {
                boolean requiredComponentPresent = jukebox.getComponents().stream().anyMatch(o -> o.getName().equals(requiredComponent));
                if (!requiredComponentPresent) return false;
            }

            return true; // Return True if the Jukebox have all required Components
        };
    }

    /**
     * Allow us to get a Paginated Result
     *
     * @param listOfJukeboxes
     * @param offset          : specifies at what index start the page
     * @param limit           : specifies the page size
     * @return paginatedResult
     */
    private List<JukeboxDTO> paginatedResult(List<JukeboxDTO> listOfJukeboxes, Integer offset, Integer limit) {

        // If offset or limit are null we return the result
        if (offset == null || limit == null) return listOfJukeboxes;

        var start = offset * limit;

        // If we go past the last page
        if (start > listOfJukeboxes.size()) return Collections.emptyList();

        return listOfJukeboxes.stream()
                .skip(start)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
