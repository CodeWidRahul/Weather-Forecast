package com.kotakbank.assignment.service.impl;

import com.kotakbank.assignment.rest.repo.WeatherForecastRestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastServiceImplTest {

    @InjectMocks
    private WeatherForecastServiceImpl weatherForecastService;

    @Mock
    private WeatherForecastRestClient weatherForecastRestClient;

    @Test
    void fetchWeatherReportTest() {
        when(weatherForecastRestClient.executeWeatherForecastRestApi(anyString())).thenReturn(new Object());
        Object report = weatherForecastService.fetchWeatherReport("");
        assertNotNull(report);
    }

    @Test
    void fetchWeatherReportTest_RestClientException() {
        when(weatherForecastRestClient.executeWeatherForecastRestApi(anyString())).thenThrow(new RestClientException("downstream"));
        assertThrows(Exception.class, () -> weatherForecastService.fetchWeatherReport(""));
    }

    @Test
    void fetchWeatherReportTest_Exception() {
        when(weatherForecastRestClient.executeWeatherForecastRestApi(anyString())).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> weatherForecastService.fetchWeatherReport(""));
    }
}
