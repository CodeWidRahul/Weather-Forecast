package com.kotakbank.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMessage {

    private List<Error> errors;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Error {
        private String errorCode;
        private String message;
    }
}
