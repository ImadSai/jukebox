package com.theoctavegroup.jukeboxsettings.dto;

import lombok.*;

import java.util.List;

/**
 * Settings Data Transfer Object
 */
@Data
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
