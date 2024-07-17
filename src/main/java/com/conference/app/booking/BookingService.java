package com.conference.app.booking;

import com.conference.app.booking.entity.Booking;
import com.conference.app.booking.entity.BookingRepo;
import com.conference.app.booking.entity.CreateBookingDTO;
import com.conference.app.booking.entity.GetAvailableRoomsDTO;
import com.conference.app.booking.strategy.FirstComeFirstServeStrategy;
import com.conference.app.booking.strategy.IBookingStrategy;
import com.conference.app.room.RoomService;
import com.conference.app.room.entity.Room;
import com.conference.app.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class BookingService {


    private final RoomService roomSrvc;

    private final BookingRepo bookingRepo;

    private final IBookingStrategy bookingStrategy;


    @Autowired
    public BookingService(BookingRepo bookingRepo, RoomService roomSrvc) {

        this.bookingRepo = bookingRepo;
        this.roomSrvc = roomSrvc;
        this.bookingStrategy = new FirstComeFirstServeStrategy(bookingRepo, roomSrvc);
    }


    private static void assertNoMaintenance(GetAvailableRoomsDTO dto) {
        for (var x : RoomService.getMaintenance()) {
            if ((Integer.parseInt(dto.startAt) >= x[0] && Integer.parseInt(dto.startAt) <= x[1])
                    || (Integer.parseInt(dto.endAt) >= x[0] && Integer.parseInt(dto.endAt) <= x[1])
                    || (Integer.parseInt(dto.startAt) <= x[0] && Integer.parseInt(dto.endAt) >= x[1]))
                throw new AppException(AppException.ERR_TYPE_MAINTENANCE);
        }
    }

    private static String round15Min(String endAtToRound) {
        // Round the EndTime
        var endAt = Integer.parseInt(endAtToRound);
        var min = (Integer.parseInt(endAtToRound) % 100);
        if (min % 15 != 0) {
            var minutesToAdd = 15 - min % 15;
            if (min + minutesToAdd == 60) {
                endAt += 100; // add a full hour
            } else {
                endAt += minutesToAdd;
            }
            return endAt + "";
        }
        return endAtToRound;
    }

    private static void assertBooking4Today(CreateBookingDTO dto) {
        if (!LocalDate.now().toString().equals(dto.bookingDate))
            throw new AppException(AppException.ERR_TYPE_INVALID_DATE);
    }

    public Optional<Booking> book(CreateBookingDTO dto) {

        // assertValidDate
        assertBooking4Today(dto);

        // assert no Maintenance within this time period
        assertNoMaintenance(dto);

        dto.endAt = round15Min(dto.endAt);

        // Get all Rooms with the needed capacity
        List<Room> rooms = roomSrvc
                .findByCapacityGreaterThanEqual(dto.attendees);
        if (rooms.isEmpty())
            throw new AppException(AppException.ERR_TYPE_EXCEED_CAPACITY);

        return bookingStrategy.bookRoom(dto);
    }


    public List<Booking> getBookings() {

        return new ArrayList<>(this.bookingRepo.findAll());

    }

    public List<Room> getAvailableRooms(GetAvailableRoomsDTO dto) {

        // assert no Maintenance within this time period
        assertNoMaintenance(dto);

        dto.endAt = round15Min(dto.endAt);
        return new ArrayList<>(this.bookingRepo.getAvailableRooms(Integer.parseInt(dto.startAt), Integer.parseInt(dto.endAt), LocalDate.now(), dto.attendees));
    }
}
