package com.conference.app.util;

public class ApiErrorResponse {

    private final int code;
    private final String message;

    public ApiErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}

