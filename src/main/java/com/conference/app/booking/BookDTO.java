package com.conference.app.booking;

import jakarta.validation.constraints.NotNull;

public class BookDTO {


    @NotNull(message = "startAt is required")
    public String startAt;
    @NotNull(message = "endAt is required")
    public String endAt;
    @NotNull(message = "attendees is required")
    public int attendees;
    @NotNull(message = "bookingDate is required")
    public String bookingDate;

    public BookDTO(String startAt, String endAt, int attendees, String bookingDate) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.attendees = attendees;
        this.bookingDate = bookingDate;
    }
}
