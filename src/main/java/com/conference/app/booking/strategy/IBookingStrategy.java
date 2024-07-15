package com.conference.app.booking.strategy;

import com.conference.app.booking.Booking;
import com.conference.app.booking.CreateBookingDTO;

import java.util.Optional;

public interface IBookingStrategy {

    public Optional<Booking> bookRoom(CreateBookingDTO dto);
}
