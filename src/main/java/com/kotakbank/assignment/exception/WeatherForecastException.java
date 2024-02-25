package com.kotakbank.assignment.exception;

import com.kotakbank.assignment.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastException extends RuntimeException {
    private ErrorCode errorCode;
}
