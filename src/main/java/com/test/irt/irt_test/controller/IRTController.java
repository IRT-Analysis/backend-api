package com.test.irt.irt_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.irt.irt_test.request.IRTRequest;

@RestController
@RequestMapping("/api/irt")
public class IRTController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/analyze")
    public ResponseEntity<?> performIRTAnalysis(@RequestBody IRTRequest irtRequest) {
        try {
            // Call the Python service
            String pythonServiceUrl = "http://localhost:5001/irt/analyze";
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl, irtRequest, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error performing IRT analysis");
        }
    }
}