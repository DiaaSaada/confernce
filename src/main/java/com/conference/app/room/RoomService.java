package com.conference.app.room;

import com.conference.app.booking.BookDTO;
import com.conference.app.booking.Booking;
import com.conference.app.booking.BookingRepo;
import com.conference.app.util.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class RoomService {


    private final RoomRepo roomRepo;

    @Autowired
    public RoomService( RoomRepo roomRepo) {

        this.roomRepo = roomRepo;
    }

    public static ArrayList<int[]> getMaintenance() {
        ArrayList<int[]> intervals = new ArrayList<>();
        intervals.add(new int[]{900, 915});
        intervals.add(new int[]{1300, 1315});
        intervals.add(new int[]{1700, 1715});
        return intervals;
    }


    public List<Room> findByCapacityGreaterThanEqual(int attendees) {

        return  roomRepo
                .findByCapacityGreaterThanEqual(attendees);

    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }
}
