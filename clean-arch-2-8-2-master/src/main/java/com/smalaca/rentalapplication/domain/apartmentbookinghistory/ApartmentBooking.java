package com.smalaca.rentalapplication.domain.apartmentbookinghistory;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APARTMENT_BOOKING")
public class ApartmentBooking {

    @Id
    @GeneratedValue
    private String id;

    private final BookingStep bookingStep;
    private final LocalDateTime bookingDateTime;
    private final String ownerId;
    private final String tenantId;
    @Embedded
    private final BookingPeriod bookingPeriod;

    private ApartmentBooking(BookingStep bookingStep, LocalDateTime bookingDateTime, String ownerId, String tenantId, BookingPeriod bookingPeriod) {
        this.bookingStep = bookingStep;
        this.bookingDateTime = bookingDateTime;
        this.ownerId = ownerId;
        this.tenantId = tenantId;
        this.bookingPeriod = bookingPeriod;
    }

    public static ApartmentBooking start(LocalDateTime bookingDateTime, String ownerId, String tenantId, BookingPeriod bookingPeriod) {
        return new ApartmentBooking(BookingStep.START, bookingDateTime, ownerId, tenantId, bookingPeriod);
    }
}
