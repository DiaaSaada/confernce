package com.conference.app.booking;

import com.conference.app.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter
 * on all fields, and @Setter on all non-final fields,
 * and @RequiredArgsConstructor!
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Booking extends BaseEntity {

    public Booking() { }

    public Booking(Long id, Long roomId, LocalDate bookDate, Integer startAt, Integer endAt) {
        this.id = id;
        this.roomId = roomId;
        this.bookDate = bookDate;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDate bookDate;

    @NotNull
    private Integer startAt;


    @NotNull
    private Integer endAt;


}
