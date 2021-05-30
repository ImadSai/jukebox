package com.theoctavegroup.jukeboxsettings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Settings Data Transfer Object
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettingWrapperDTO {

    /**
     * List of Setting properties
     */
    List<SettingPropertiesDTO> settings;

}
