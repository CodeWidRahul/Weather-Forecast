package com.kotakbank.assignment.service.impl;

import com.kotakbank.assignment.constant.ErrorCode;
import com.kotakbank.assignment.exception.WeatherForecastException;
import com.kotakbank.assignment.rest.repo.WeatherForecastRestClient;
import com.kotakbank.assignment.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {

    @Autowired
    private WeatherForecastRestClient weatherForecastRestClient;

    @Override
    public Object fetchWeatherReport(String location) {
        try {
            return weatherForecastRestClient.executeWeatherForecastRestApi(location);
        } catch (RestClientException exception) {
            throw new WeatherForecastException(ErrorCode.DOWN_STREAM_ERROR);
        } catch (Exception exception) {
            throw new WeatherForecastException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
