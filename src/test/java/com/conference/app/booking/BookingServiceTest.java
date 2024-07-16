package com.conference.app.booking;

import com.conference.app.booking.entity.Booking;
import com.conference.app.booking.entity.BookingRepo;
import com.conference.app.booking.entity.CreateBookingDTO;
import com.conference.app.room.entity.Room;
import com.conference.app.room.RoomService;
import com.conference.app.util.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
class BookingServiceTest {


    @MockBean
    private BookingService bookingService;

    @MockBean
    private RoomService roomService;  // Mock your RoomService

    @Autowired
    private BookingRepo bookingRepo;

    private static List<Room> getRooms() {
        List<Room> rooms = List.of(
                new Room(1L, "Amaze", 3),
                new Room(2L, "Beauty", 7),
                new Room(3L, "Inspire", 12),
                new Room(4L, "Strive", 20)
        );
        return rooms;
    }

    public void setup() {

        roomService = mock(RoomService.class);
        //bookingService = new BookingService(mock(BookingRepo.class), roomService);
        bookingService = new BookingService(bookingRepo, roomService);
        // Seed rooms for testing (Mock)
        when(roomService.getAllRooms()).thenReturn(getRooms());
    }

    @Test
    void book_duringMaintenance() {
        setup();
        CreateBookingDTO dto = new CreateBookingDTO("0900", "0930", 1, LocalDate.now().toString()); // During maintenance
        var exception = assertThrows(AppException.class, () -> bookingService.book(dto));
        assertEquals(AppException.ERR_TYPE_MAINTENANCE, exception.getMessage());
    }

    @Test
    void book_invalidDate() {
        setup();
        CreateBookingDTO dto = new CreateBookingDTO("0800", "0900", 1, LocalDate.now().plusDays(1).toString());
        var exception = assertThrows(AppException.class, () -> bookingService.book(dto));
        assertEquals(AppException.ERR_TYPE_INVALID_DATE, exception.getMessage());
    }

    @Test
    void book_exceedCapacity() {
        setup();
        CreateBookingDTO dto = new CreateBookingDTO("0800", "0830", 100, LocalDate.now().toString());
        var exception = assertThrows(AppException.class, () -> bookingService.book(dto));
        assertEquals(AppException.ERR_TYPE_EXCEED_CAPACITY, exception.getMessage());
    }


    @Test
    void book_validBooking() {
        setup();
        CreateBookingDTO dto = new CreateBookingDTO("0800", "0830", 5, LocalDate.now().toString());

        when(roomService.findByCapacityGreaterThanEqual(5)).thenReturn(getRooms().stream().filter(r -> r.getCapacity() >= 5).toList());


        Optional<Booking> result = bookingService.book(dto);
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getRoomId());

        Optional<Booking> result3 = bookingService.book(dto);
        assertTrue(result3.isPresent());
        assertEquals(3L, result3.get().getRoomId());

        Optional<Booking> result4 = bookingService.book(dto);
        assertTrue(result4.isPresent());
        assertEquals(4L, result4.get().getRoomId());
    }

    @Test
    void book_validBooking2() {
        setup();
        CreateBookingDTO dto = new CreateBookingDTO("0800", "0830", 5, LocalDate.now().toString());

        when(roomService.findByCapacityGreaterThanEqual(5)).thenReturn(getRooms().stream().filter(r -> r.getCapacity() >= 5).toList());


        Optional<Booking> result = bookingService.book(dto);
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getRoomId());

        Optional<Booking> result3 = bookingService.book(dto);
        assertTrue(result3.isPresent());
        assertEquals(3L, result3.get().getRoomId());

        Optional<Booking> result4 = bookingService.book(dto);
        assertTrue(result4.isPresent());
        assertEquals(4L, result4.get().getRoomId());
    }


}