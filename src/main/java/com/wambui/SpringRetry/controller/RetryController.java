package com.wambui.SpringRetry.controller;

import com.wambui.SpringRetry.service.RetryService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    private RetryService retryService;

    @PostMapping("/data")
    public ResponseEntity<String> postData(@RequestBody String data){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(data, headers);
        try{
            return retryService.sendCallbackToApigee("https://apistg.safaricom.co.ke/data", request);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }

