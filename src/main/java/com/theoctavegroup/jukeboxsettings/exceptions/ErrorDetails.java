package com.theoctavegroup.jukeboxsettings.exceptions;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails implements Serializable {

    private Date timestamp;
    private String message;

}
