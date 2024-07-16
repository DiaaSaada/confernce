package com.conference.app.booking.strategy;

import com.conference.app.booking.entity.Booking;
import com.conference.app.booking.entity.CreateBookingDTO;

import java.util.Optional;

public interface IBookingStrategy {

    public Optional<Booking> bookRoom(CreateBookingDTO dto);
}
