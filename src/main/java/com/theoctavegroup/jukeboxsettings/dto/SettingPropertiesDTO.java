package com.theoctavegroup.jukeboxsettings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Setting Properties Transfer Object
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettingPropertiesDTO {

    /**
     * Settings id
     */
    String id;

    /**
     * List of Material required
     */
    List<String> requires;

}
