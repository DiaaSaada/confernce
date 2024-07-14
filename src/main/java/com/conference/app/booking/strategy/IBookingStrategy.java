package com.conference.app.booking.strategy;

import com.conference.app.booking.BookDTO;
import com.conference.app.booking.Booking;

import java.util.Optional;

public interface IBookingStrategy {

    public Optional<Booking> bookRoom(BookDTO dto);
}
