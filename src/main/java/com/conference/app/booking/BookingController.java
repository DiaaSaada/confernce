package com.conference.app.booking;

import com.conference.app.util.AppException;
import com.conference.app.validators.MilitaryTimeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/booking")
public class BookingController {


    private final BookingService bookingSrvc;
    private final BookingRepo bookingRepo;

    @Autowired
    public BookingController(BookingService bookingSrvc, BookingRepo bookingRepo
    ) {
        this.bookingSrvc = bookingSrvc;
        this.bookingRepo = bookingRepo;
    }


    @PostMapping
    public Optional<Booking> book(@Valid @RequestBody BookDTO dto) {

        if (!MilitaryTimeValidator.isValidMilitaryTime(dto.startAt)) {
            throw new AppException("Invalid startAt: " + dto.startAt);
        }
        if (!MilitaryTimeValidator.isValidMilitaryTime(dto.endAt)) {
            throw new AppException("Invalid endAt: " + dto.endAt);
        }
        return this.bookingSrvc.book(dto);

    }

    @GetMapping
    public List<Booking> getBookings() {

        var booking = new Booking();
        booking.setId(123L);
        booking.setEndAt(123);
        booking.setStartAt(123);
        booking.setRoomId(34535345L);
        booking.setBookDate(LocalDate.now());
        Optional.of(bookingRepo.save(booking));
        return new ArrayList<>(this.bookingRepo.findAll());

    }


}
