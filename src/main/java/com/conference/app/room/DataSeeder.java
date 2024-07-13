package com.conference.app.room;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepo roomRepository;

    @Autowired
    public DataSeeder(RoomRepo roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to avoid duplications
        if (roomRepository.count() == 0) {
            Room room1 = new Room();
            room1.setName("Amaze");
            room1.setCapacity(3);

            Room room2 = new Room();
            room2.setName("Beauty");
            room2.setCapacity(7);

            Room room3 = new Room();
            room3.setName("Inspire");
            room3.setCapacity(12);

            Room room4 = new Room();
            room4.setName("Strive");
            room4.setCapacity(20);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            roomRepository.save(room4);

            System.out.println("Data seeding completed.");
        } else {
            System.out.println("Data already exists.");
        }
    }
}

