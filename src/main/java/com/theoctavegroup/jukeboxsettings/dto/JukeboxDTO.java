package com.theoctavegroup.jukeboxsettings.dto;

import lombok.*;

import java.util.List;

/**
 * Jukebox Data Transfer Object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JukeboxDTO {

    /**
     * Jukebox Id
     */
    String id;

    /**
     * Jukebox Model
     */
    String model;

    /**
     * Jukebox Components
     */
    List<JukeboxComponentDTO> components;

}
