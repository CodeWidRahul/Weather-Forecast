package com.kotakbank.assignment.exception.handler;

import com.kotakbank.assignment.constant.ErrorCode;
import com.kotakbank.assignment.exception.WeatherForecastException;
import com.kotakbank.assignment.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class KtbExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleWeatherForecastException(WeatherForecastException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorMessage.builder()
                        .errors(List.of(ErrorMessage.Error.builder()
                                .errorCode(errorCode.getErrorCode())
                                .message(errorCode.getDetail())
                                .build()))
                        .build());
    }
}
