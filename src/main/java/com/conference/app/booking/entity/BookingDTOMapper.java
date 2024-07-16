package com.conference.app.booking.entity;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookingDTOMapper implements Function<Booking, BookingDTO> {


    @Override
    public BookingDTO apply(Booking booking) {
        return new BookingDTO(
                booking.getRoomId(),
                booking.getAttendees(),
                booking.getBookDate().toString(),
                String.format("%04d", booking.getStartAt()),
                String.format("%04d", booking.getEndAt())
        );
    }
}
