# Conference Booking

## Setup
* The project is using port 8080
* The project is using H2 database( in memory database)
* The project is using JPA provider Hibernate
* The project is using Spring Boot version 3.2.4
* The project is using Java version 22


## How to run

* Clone the project
* Run the project using the following command

```docker build -t conference-booking .```

```docker run -p 8080:8080 conference-booking```


--- OR WITH MVN ---

```mvn spring-boot:run```

## How to run tests

```mvn test```


## How to use

Create new booking
```
curl --location 'http://localhost:8080/api/v1/booking' \
--header 'Content-Type: application/json' \
--data '{
  "attendees":  7,
  "bookingDate": "2024-07-18",
  "startAt": "1900",
  "endAt": "1930"
}'
```

Get all conferences
```
curl --location 'http://localhost:8080/api/v1/booking'
```

## Notes:
* The project is using Lombok to reduce boilerplate code
* The project is using Spring Data JPA to interact with the database
* JUnit is used for testing
* Inner service will throw AppException which will be caught by the CustomExceptionHandler return 400 HTTP response
* Jakarta will throw MethodArgumentNotValidException which will be caught by the CustomExceptionHandler return 400 HTTP response
* The project is expecting & saving Military time, which is 24 hours format e.g. 1515 for 3:15 PM which is easier to validate and manipulate
* Data seeding of the rooms are seeded via CommandLineRunner by com.conference.app.room.seeder.DataSeeder class 