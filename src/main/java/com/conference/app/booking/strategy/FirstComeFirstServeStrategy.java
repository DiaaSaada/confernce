package com.conference.app.booking.strategy;

import com.conference.app.booking.entity.Booking;
import com.conference.app.booking.entity.BookingRepo;
import com.conference.app.booking.entity.CreateBookingDTO;
import com.conference.app.room.entity.Room;
import com.conference.app.room.RoomService;
import com.conference.app.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Component
@Primary  // means this will be wired by default for DI
public class FirstComeFirstServeStrategy implements IBookingStrategy {


    private final RoomService roomSrvc;
    private final BookingRepo bookingRepo;

    @Autowired
    public FirstComeFirstServeStrategy(BookingRepo srvc, RoomService roomSrvc) {

        this.bookingRepo = srvc;
        this.roomSrvc = roomSrvc;
    }

    /**
     * @param dto BookDTO
     * @return Optional<Booking>
     * @throws AppException
     */
    @Override
    public Optional<Booking> bookRoom(CreateBookingDTO dto) {

        List<Room> rooms = roomSrvc
                .findByCapacityGreaterThanEqual(dto.attendees);
        for (Room room : rooms) {
            Optional<Booking> booking = bookingRepo.findBooking(room.getId(),
                    Integer.parseInt(dto.startAt),
                    Integer.parseInt(dto.endAt)); // check if room is available
            if (booking.isEmpty()) {
                var newBooking = new Booking();
                newBooking.setEndAt(Integer.parseInt(dto.endAt));
                newBooking.setStartAt(Integer.parseInt(dto.startAt));
                newBooking.setRoomId(room.getId());
                newBooking.setBookDate(LocalDate.now());
                newBooking.setAttendees(dto.attendees);
                return Optional.of(bookingRepo.save(newBooking));
            }
        }
        throw new AppException(AppException.ERR_TYPE_NO_ROOMS_IN_TIME_SLOT);
    }
}
