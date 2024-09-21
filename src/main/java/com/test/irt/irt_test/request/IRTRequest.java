package com.test.irt.irt_test.request;

import java.util.List;

public class IRTRequest {
    private List<List<Integer>> responses; // Nested list of responses, 0 or 1 for each item by each respondent

    // Optional fields
    private String testName;
    private String respondentId;

    // Getters and setters
    public List<List<Integer>> getResponses() {
        return responses;
    }

    public void setResponses(List<List<Integer>> responses) {
        this.responses = responses;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(String respondentId) {
        this.respondentId = respondentId;
    }
}
