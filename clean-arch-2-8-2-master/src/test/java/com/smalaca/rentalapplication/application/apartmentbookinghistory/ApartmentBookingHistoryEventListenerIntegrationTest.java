package com.smalaca.rentalapplication.application.apartmentbookinghistory;

import com.google.common.collect.ImmutableMap;
import com.smalaca.rentalapplication.application.apartment.ApartmentApplicationService;
import com.smalaca.rentalapplication.domain.apartment.Apartment;
import com.smalaca.rentalapplication.domain.apartment.ApartmentFactory;
import com.smalaca.rentalapplication.domain.apartment.Period;
import com.smalaca.rentalapplication.domain.apartmentbookinghistory.ApartmentBooking;
import com.smalaca.rentalapplication.domain.apartmentbookinghistory.ApartmentBookingAssertion;
import com.smalaca.rentalapplication.domain.apartmentbookinghistory.ApartmentBookingHistory;
import com.smalaca.rentalapplication.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ApartmentBookingHistoryEventListenerIntegrationTest {

    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String TENANT_IT = "137";
    private static final LocalDate START = LocalDate.of(2020, 3, 4);
    private static final LocalDate MIDDLE = LocalDate.of(2020, 3, 5);
    private static final LocalDate END = LocalDate.of(2020, 3, 6);
    private static final Period PERIOD = new Period(START, END);

    private final ApartmentFactory apartmentFactory = new ApartmentFactory();

    @Autowired
    private ApartmentApplicationService apartmentApplicationService;

    @Autowired
    private ApartmentBookingHistoryRepository apartmentBookingHistoryRepository;

    @Test
    @Transactional
    void shouldUpdateApartmentBookingHistory() {
        String apartmentId = givenExistingApartment();

        apartmentApplicationService.book(apartmentId, TENANT_IT, START, END);
        ApartmentBookingHistory actual = apartmentBookingHistoryRepository.findFor(apartmentId);

        Assertions.assertThat(actual).extracting("bookings").satisfies(actualBookings -> {
            List<ApartmentBooking> bookings = (List<ApartmentBooking>) actualBookings;

            Assertions.assertThat(bookings).hasSize(1)
                    .allSatisfy(booking -> {
                        ApartmentBookingAssertion.assertThat(booking)
                                .hasOwnerIdEqualTo(OWNER_ID)
                                .hasTenantIdEqualTo(TENANT_IT)
                                .hasBookingPeriodThatHas(START,END);
                    });
        });

    }

    private String givenExistingApartment() {

        return null;
    }


    private Apartment createApartment() {
        return apartmentFactory.create(
                OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY,
                DESCRIPTION, ROOMS_DEFINITION);
    }

}