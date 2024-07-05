package com.deiz0n.makeorderapi.domain.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {

    private Instant instant;
    private String title;
    private String description;
    private Integer status;

}
