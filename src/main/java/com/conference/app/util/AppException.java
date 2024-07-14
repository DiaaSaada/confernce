package com.conference.app.util;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppException extends RuntimeException {

    public final static String ERR_TYPE_MAINTENANCE = "Rooms are not available due to scheduled Maintenance!";
    public final static String ERR_TYPE_INVALID_DATE = "INVALID DATE!";
    public final static String ERR_TYPE_EXCEED_CAPACITY = "NO ROOMS AVAILABLE WITH THIS Capacity!";
    public final static String ERR_TYPE_NO_ROOMS_IN_TIME_SLOT = "NO ROOMS AVAILABLE FOR THIS TIME SLOT";
    public AppException(String message) {
        super(message);
    }
}