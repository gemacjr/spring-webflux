package com.example.webfluxdemovs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Response {
    private LocalDate localDate = LocalDate.now();
    private int output;

    public Response(int output) {
        this.output = output;
    }
}
