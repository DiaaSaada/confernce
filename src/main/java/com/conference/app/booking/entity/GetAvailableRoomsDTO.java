package com.conference.app.booking.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class GetAvailableRoomsDTO {


    @NotNull(message = "startAt is required")
    @Size(min = 4, max = 4, message = "startAt must be 4 characters")
    public String startAt;

    @NotNull(message = "endAt is required")
    @Size(min = 4, max = 4, message = "startAt must be 4 characters")
    public String endAt;

    @Min(value = 1, message = "attendees should not be less than 1")
    @Max(value = 9999, message = "attendees should not be greater than 9999")
    public int attendees;


    public GetAvailableRoomsDTO(String startAt, String endAt, int attendees ) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.attendees = attendees;
    }
}
