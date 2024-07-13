package com.conference.app.room;

import com.conference.app.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter
 * on all fields, and @Setter on all non-final fields,
 * and @RequiredArgsConstructor!
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int capacity;

}
