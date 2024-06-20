package com.deiz0n.makeorderapi.domain.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@AllArgsConstructor
public class ResponseRequest {

    private Instant instant;
    private String title;
    private String description;
    private Integer status;

}
