package com.kotakbank.assignment.rest.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherForecastRestClient {

    @Value("${weather.forecast.baseurl}")
    private String url;

    @Value("${weather.forecast.x-rapidapi-key}")
    private String xRapidApiKey;

    @Value("${weather.forecast.x-rapidapi-host}")
    private String xRapidApiHost;

    public Object executeWeatherForecastRestApi(String location) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(getHttpHeader());
        Map<String, String> params = getParams(location);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> exchange = restTemplate
                .exchange(url.concat("/current.json?q={q}"), HttpMethod.GET, requestEntity, Object.class, params);
        return exchange.getBody();
    }

    private Map<String, String> getParams(String location) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", location);
        return params;
    }

    private HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Rapidapi-Key", xRapidApiKey);
        headers.set("X-Rapidapi-Host", xRapidApiHost);
        return headers;
    }
}
