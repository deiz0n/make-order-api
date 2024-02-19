package com.deiz0n.makeorderapi.api.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@AllArgsConstructor
@Data
public class Error {

    private String title;
    private String detail;
    private HttpStatus status;
    private Instant data;
    private String uri;

}
