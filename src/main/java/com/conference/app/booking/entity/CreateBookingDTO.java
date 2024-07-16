package com.conference.app.booking.entity;

import jakarta.validation.constraints.*;

public class CreateBookingDTO {


    @NotNull(message = "startAt is required")
    @Size(min = 4, max = 4, message = "startAt must be 4 characters")
    public String startAt;

    @NotNull(message = "endAt is required")
    @Size(min = 4, max = 4, message = "startAt must be 4 characters")
    public String endAt;

    @NotNull(message = "attendees is required")
    @Min(value = 1, message = "attendees should not be less than 1")
    @Max(value = 9999, message = "attendees should not be greater than 9999")
    public int attendees;

    @NotNull(message = "bookingDate is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in the format yyyy-MM-dd")
    public String bookingDate;

    public CreateBookingDTO(String startAt, String endAt, int attendees, String bookingDate) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.attendees = attendees;
        this.bookingDate = bookingDate;
    }
}
