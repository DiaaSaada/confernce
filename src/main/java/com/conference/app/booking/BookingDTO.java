package com.conference.app.booking;

public record BookingDTO(Long roomId,
                         int attendees,
                         String bookingDate,
                         String startAt,
                         String endAt) {
}
