package com.deiz0n.makeorderapi.domain.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Instant instant;
    private String title;
    private String description;
    private HttpStatus status;
    private String path;

}
