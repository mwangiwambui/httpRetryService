package com.wambui.SpringRetry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;

@Service
public class RetryService {
    private RestTemplate restTemplate;

    @Retryable(value = {HttpClientErrorException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))

    public ResponseEntity<String> sendCallbackToApigee(String url, HttpEntity<String> request)
        throws Exception{
        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
    public String sendToApigee(String data) {
        return restTemplate.postForObject("http://apiCalll.com", data, String.class);
    }
}
