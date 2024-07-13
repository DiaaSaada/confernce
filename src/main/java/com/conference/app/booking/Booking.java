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
