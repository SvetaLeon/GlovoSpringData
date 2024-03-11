package com.company.glovospringdata.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse <D> {
    private boolean success;
    private D data;
    private List<String> messages;

    public void addMessage(String message) {
        messages.add(message);
    }
}
