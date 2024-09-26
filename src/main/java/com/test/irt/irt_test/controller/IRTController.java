package com.test.irt.irt_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/irt")
public class IRTController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Prepare the multipart request to send the file to Flask
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create the form data to send (key is 'file', matching Flask's key)
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // Set the filename for Flask
                }
            });

            // Create HttpEntity for the request
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send the file to the Flask service
            String flaskUrl = "http://localhost:5000/api/irt/analyze"; // Flask service URL
            ResponseEntity<List> flaskResponse = restTemplate.postForEntity(flaskUrl, requestEntity, List.class);

            // Create a response to return to the client
            response.put("message", "File uploaded and processed successfully by Flask");
            response.put("data", flaskResponse.getBody());

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "Failed to process file");
            return ResponseEntity.status(500).body(response);
        }
    }
}
