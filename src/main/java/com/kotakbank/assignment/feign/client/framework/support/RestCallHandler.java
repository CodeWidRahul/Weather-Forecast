package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.exception.RestCallException;
import com.kotakbank.assignment.feign.client.framework.model.CallDataModel;
import com.kotakbank.assignment.feign.client.framework.status.code.FeignErrorMessage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;

public class RestCallHandler {

    private final RestTemplate restTemplate;

    public RestCallHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static ParameterizedTypeReference<?> getParameterizedTypeReference(CallDataModel callDataModel) {
        ParameterizedTypeReference<?> parameterizedTypeReference = new ParameterizedTypeReference<Object>() {
            @Override
            public Type getType() {
                return callDataModel.getReturnType();
            }
        };
        return parameterizedTypeReference;
    }

    public Object handleRestCall(CallDataModel callDataModel) throws RestCallException {
        try {
            HttpEntity httpEntity = new HttpEntity(callDataModel.getHttpHeaders());
            //Need to set these 2 -> callDataModel.getPathParams();callDataModel.getQueryParams();
            if (callDataModel.getReturnType() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) callDataModel.getReturnType();
                Class<?> returnClass = (Class<?>) parameterizedType.getRawType();
                Class<?> actualTypeArgument = (Class<?>) ((ParameterizedType) callDataModel.getReturnType()).getActualTypeArguments()[0];
                if (returnClass.isAssignableFrom(ResponseEntity.class)) {
                    return restTemplate.exchange(callDataModel.getUrl(), callDataModel.getMethodToCall(), httpEntity, actualTypeArgument);
                } else if (isCollection(returnClass)) {
                    ResponseEntity<?> responseEntity = restTemplate.exchange(callDataModel.getUrl(), callDataModel.getMethodToCall(), httpEntity,
                            getParameterizedTypeReference(callDataModel));
                    if (responseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(responseEntity.getBody()))
                        return responseEntity.getBody();
                    else
                        throw new RestCallException(FeignErrorMessage.INVALID_RESPONSE, null, HttpStatus.NOT_ACCEPTABLE, "");
                } else
                    throw new RestCallException(String.format(FeignErrorMessage.INVALID_RETURN_VALUE, returnClass.getName()), null, HttpStatus.NOT_ACCEPTABLE, "");
            } else {
                Class<?> returnClass = (Class<?>) callDataModel.getReturnType();
                ResponseEntity<?> responseEntity = restTemplate.exchange(callDataModel.getUrl(), callDataModel.getMethodToCall(), httpEntity, returnClass);
                if (responseEntity.getStatusCode().is2xxSuccessful() && Objects.nonNull(responseEntity.getBody()))
                    return responseEntity.getBody();
                else
                    throw new RestCallException(FeignErrorMessage.INVALID_RESPONSE, null, HttpStatus.NOT_ACCEPTABLE, "");
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new RestCallException(httpClientErrorException.getMessage(), httpClientErrorException.getCause(), httpClientErrorException.getStatusCode(), httpClientErrorException.getResponseBodyAsString());
        } catch (RestClientException restClientException) {
            throw new RestCallException(restClientException.getMessage(), restClientException.getCause(), HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    private boolean isCollection(Class<?> returnType) {
        return Collection.class.isAssignableFrom(returnType);
    }
}
