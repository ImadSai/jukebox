package com.theoctavegroup.jukeboxsettings.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;

@Getter
public class WebClientCustomException extends WebClientException {

    private HttpStatus status;

    public WebClientCustomException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
