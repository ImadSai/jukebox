package com.theoctavegroup.jukeboxsettings.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails implements Serializable {

    private final Date timestamp;
    private final String message;

}
