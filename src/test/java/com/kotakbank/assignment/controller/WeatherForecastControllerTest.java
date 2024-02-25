package com.kotakbank.assignment.controller;

import com.kotakbank.assignment.service.WeatherForecastService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastControllerTest {
    @InjectMocks
    private WeatherForecastController weatherForecastController;

    @Mock
    private WeatherForecastService weatherForecastService;

    @Test
    void getWeatherReportTest() {
        when(weatherForecastService.fetchWeatherReport(anyString())).thenReturn(new Object());
        ResponseEntity<?> responseEntity = weatherForecastController.getWeatherReport("");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
