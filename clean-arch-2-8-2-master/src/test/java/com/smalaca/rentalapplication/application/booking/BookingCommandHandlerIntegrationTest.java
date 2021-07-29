package com.smalaca.rentalapplication.application.booking;

import com.smalaca.rentalapplication.domain.apartment.Booking;
import com.smalaca.rentalapplication.domain.apartment.BookingAssertion;
import com.smalaca.rentalapplication.domain.apartment.BookingRepository;
import com.smalaca.rentalapplication.infrastructure.rest.api.booking.BookingRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static java.util.Arrays.asList;

@SpringBootTest
class BookingCommandHandlerIntegrationTest {

    @Autowired
    private BookingRestController bookingRestController;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void shouldAcceptBooking(){
        String bookingId = givenOpenBooking();
        bookingRestController.accept(bookingId);

        Booking actual = bookingRepository.findById(bookingId);

        BookingAssertion.assertThat(actual).isAccepted();
    }

    @Test
    void shouldRejectBooking(){
        String bookingId = givenOpenBooking();
        bookingRestController.reject(bookingId);

        Booking actual = bookingRepository.findById(bookingId);

        BookingAssertion.assertThat(actual).isRejected();
    }

    private String givenOpenBooking() {

        Booking booking = Booking.hotelRoom("123","345",asList(LocalDate.now(),LocalDate.now().plusDays(1)));
        return bookingRepository.save(booking);
    }


}