package com.wambui.SpringRetry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RetryService implements RetryCallback<ResponseEntity<String>, Exception> , RecoveryCallback<ResponseEntity<String>> {

    private final HttpEntity<String> request;
    private final RestTemplate restTemplate;

    public RetryService(HttpEntity<String> request, RestTemplate restTemplate) {
        this.request = request;
        this.restTemplate = restTemplate;
    }

    @Retryable(value = {HttpClientErrorException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))

    @Override
    public ResponseEntity<String> doWithRetry(RetryContext retryContext) throws Exception {
        return restTemplate.exchange("http://api.call.com/api", HttpMethod.POST, request, String.class);
    }
    @Recover
    @Override
    public ResponseEntity<String> recover(RetryContext context) {
        return new ResponseEntity<>("API call failed after multiple retries", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
