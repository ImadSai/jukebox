package com.theoctavegroup.jukeboxsettings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Jukebox Data Transfer Object
 */
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
