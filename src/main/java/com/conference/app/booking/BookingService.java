package com.conference.app.booking;

import com.conference.app.room.Room;
import com.conference.app.room.RoomRepo;
import com.conference.app.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class BookingService {


    private final RoomRepo roomRepo;
    BookingRepo bookingRepo;

    @Autowired
    public BookingService(BookingRepo srvc, RoomRepo roomRepo) {

        this.bookingRepo = srvc;
        this.roomRepo = roomRepo;
    }

    public static ArrayList<int[]> getMaintenance() {
        ArrayList<int[]> intervals = new ArrayList<>();
        intervals.add(new int[]{900, 915});
        intervals.add(new int[]{1300, 1315});
        intervals.add(new int[]{1700, 1715});
        return intervals;
    }

    private static void assertNoMaintenance(BookDTO dto) {
        for (var x : getMaintenance()) {
            if ((Integer.parseInt(dto.startAt) >= x[0] && Integer.parseInt(dto.startAt) <= x[1])
                    || (Integer.parseInt(dto.endAt) >= x[0] && Integer.parseInt(dto.endAt) <= x[1])
                    || (Integer.parseInt(dto.startAt) <= x[0] && Integer.parseInt(dto.endAt) >= x[1]))
                throw new AppException("Rooms are not available due to scheduled Maintenance!");
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

    private static void assertBokingisToday(BookDTO dto) {
        if (!LocalDate.now().toString().equals(dto.bookingDate))
            throw new AppException("INVALID DATE:" + dto.bookingDate);
    }

    public Optional<Booking> book(BookDTO dto) {

        // assertValidDate
        assertBokingisToday(dto);

        // assert no Maintenance within this time period
        assertNoMaintenance(dto);

        dto.endAt = round15Min(dto.endAt);

        // Get the Smallest Room

        List<Room> rooms = roomRepo
                .findByCapacityGreaterThanEqual(dto.attendees);
        if (rooms.isEmpty())
            throw new AppException("NO ROOMS AVAILABLE");

        System.out.println(rooms.getFirst().getName());
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
                return Optional.of(bookingRepo.save(newBooking));
            }
        }
        throw new AppException("NO ROOMS AVAILABLE FOR THIS TIME SLOT");
    }
}
