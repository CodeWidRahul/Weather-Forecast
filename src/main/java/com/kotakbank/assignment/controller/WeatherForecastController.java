package com.kotakbank.assignment.controller;

import com.kotakbank.assignment.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weather/forecast")
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    //@GetMapping(value = "/report")
    public ResponseEntity<?> getWeatherReport(@RequestParam String location) {
        Object report = weatherForecastService.fetchWeatherReport(location);
        return ResponseEntity.ok(report);
    }
}
