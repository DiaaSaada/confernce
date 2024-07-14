package com.conference.app.booking;

import com.conference.app.booking.strategy.IBookingStrategy;
import com.conference.app.room.Room;
import com.conference.app.room.RoomService;
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

    @Autowired
    private IBookingStrategy bookingStrategy;

    @Autowired
    public BookingService(BookingRepo srvc, RoomService roomSrvc) {

        this.bookingRepo = srvc;
        this.roomSrvc = roomSrvc;
    }


    private static void assertNoMaintenance(BookDTO dto) {
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

    private static void assertBooking4Today(BookDTO dto) {
        if (!LocalDate.now().toString().equals(dto.bookingDate))
            throw new AppException(AppException.ERR_TYPE_INVALID_DATE);
    }

    public Optional<Booking> book(BookDTO dto) {

        // assertValidDate
        assertBooking4Today(dto);

        // assert no Maintenance within this time period
        assertNoMaintenance(dto);

        dto.endAt = round15Min(dto.endAt);

        // Get the Smallest Room
        List<Room> rooms = roomSrvc
                .findByCapacityGreaterThanEqual(dto.attendees);
        if (rooms.isEmpty())
            throw new AppException(AppException.ERR_TYPE_EXCEED_CAPACITY);

        return bookingStrategy.bookRoom(dto);
        /*for (Room room : rooms) {
            Optional<Booking> booking = bookingRepo.findBooking(room.getId(),
                    Integer.parseInt(dto.startAt),
                    Integer.parseInt(dto.endAt)); // check if room is available
            if (booking.isEmpty()) {
                var newBooking = new Booking();
                newBooking.setEndAt(Integer.parseInt(dto.endAt));
                newBooking.setStartAt(Integer.parseInt(dto.startAt));
                newBooking.setRoomId(room.getId());
                newBooking.setBookDate(LocalDate.now());
                return Optional.of(bookingRepo.save(newBooking));
            }
        }
        throw new AppException(AppException.ERR_TYPE_NO_ROOMS_IN_TIME_SLOT);*/
    }


    public List<Booking> getBookings() {

        return new ArrayList<>(this.bookingRepo.findAll());

    }
}
