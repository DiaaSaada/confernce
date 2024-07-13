package com.conference.app.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    Optional<Booking> findByRoomId(Long roomId);

    // [s]  || [e]  || s[]e
    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND ( (:startAt >= b.startAt AND :startAt <= b.endAt) OR  (:startAt <= b.startAt AND :endAt >= b.endAt) OR  (:endAt > b.startAt AND :endAt <= b.endAt))  ORDER BY b.id ASC")
    Optional<Booking> findBooking(@Param("roomId") Long roomId,
                                  @Param("startAt") int startAt,
                                  @Param("endAt") int endAt);

}
