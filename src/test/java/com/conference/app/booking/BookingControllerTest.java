package com.conference.app.booking;

import com.conference.app.booking.entity.Booking;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {


    @Autowired
    private MockMvc mockMvc;

    // Mock any dependencies of BookingController if they exist
    @MockBean
    private BookingService bookingService; // Example service, change based on your actual service

    @Test
    public void getBookings() throws Exception {
        // Mock the service response
        when(bookingService.getBookings()).thenReturn(List.of(
                new Booking(1L, 1L, LocalDate.now(), 1100, 1200, 5),
                new Booking(2L, 2L, LocalDate.now(), 1100, 1200, 5),
                new Booking(3L, 2L, LocalDate.now(), 1100, 1200, 5),
                new Booking(4L, 3L, LocalDate.now(), 1100, 1200, 5)
        ));

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/v1/booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].roomId").value(1L))
                .andExpect(jsonPath("$[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].startAt").value(1100))
                .andExpect(jsonPath("$[0].endAt").value(1200));
    }
}