package com.theoctavegroup.jukeboxsettings.dto;

import lombok.*;

import java.util.List;

/**
 * Setting Properties Transfer Object
 */
@Data
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
