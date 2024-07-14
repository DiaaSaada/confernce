package com.conference.app.booking.strategy;

import com.conference.app.booking.BookDTO;
import com.conference.app.booking.Booking;
import com.conference.app.booking.BookingRepo;
import com.conference.app.room.RoomService;
import com.conference.app.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Component
public class PriorityBookingStrategy implements IBookingStrategy {


    @Autowired
    public PriorityBookingStrategy(BookingRepo srvc, RoomService roomSrvc) {

    }

    /**
     * @param dto BookDTO
     * @return Optional<Booking>
     * @throws AppException
     */
    @Override
    public Optional<Booking> bookRoom(BookDTO dto) {

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "PriorityBooking feature is not yet implemented");
    }
}
