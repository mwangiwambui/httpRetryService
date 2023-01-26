package com.wambui.SpringRetry.controller;

import com.wambui.SpringRetry.configuration.RestTemplateClass;
import com.wambui.SpringRetry.configuration.RestTemplateClass;
import com.wambui.SpringRetry.service.RetryService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    private final RetryService retryService;
    private final RestTemplateClass restTemplate;

    public RetryController(RetryService retryService, RestTemplateClass restTemplate) {
        this.retryService = retryService;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value="/data")
    public ResponseEntity<String> postData(@RequestBody String data){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(data, headers);
        RetryService retryService = new RetryService(request, restTemplate);
        try{
            System.out.print("Hello Girl");
            return retryService.doWithRetry(null);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }

