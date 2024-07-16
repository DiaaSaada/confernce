package com.conference.app.booking;

import com.conference.app.booking.entity.BookingDTO;
import com.conference.app.booking.entity.BookingDTOMapper;
import com.conference.app.booking.entity.CreateBookingDTO;
import com.conference.app.booking.entity.GetAvailableRoomsDTO;
import com.conference.app.room.entity.Room;
import com.conference.app.util.AppException;
import com.conference.app.validators.MilitaryTimeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/booking")
public class BookingController {


    private final BookingService bookingSrvc;
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingSrvc,
                             BookingService bookingService) {
        this.bookingSrvc = bookingSrvc;
        this.bookingService = bookingService;
    }


    @PostMapping
    public Optional<BookingDTO> book(@Valid @RequestParam CreateBookingDTO dto) {

        if (!MilitaryTimeValidator.isValidMilitaryTime(dto.startAt)) {
            throw new AppException("Invalid startAt: " + dto.startAt);
        }
        if (!MilitaryTimeValidator.isValidMilitaryTime(dto.endAt)) {
            throw new AppException("Invalid endAt: " + dto.endAt);
        }
        return this.bookingSrvc.book(dto).map(new BookingDTOMapper());

    }

    @GetMapping
    public List<BookingDTO> getBookings() {

        return bookingService.getBookings().stream().map(new BookingDTOMapper()).collect(Collectors.toList());

    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms(@RequestParam String startAt, @RequestParam String endAt, @RequestParam int attendees) {
        var dto = new GetAvailableRoomsDTO(startAt, endAt, attendees);
        return bookingService.getAvailableRooms(dto);

    }


}
