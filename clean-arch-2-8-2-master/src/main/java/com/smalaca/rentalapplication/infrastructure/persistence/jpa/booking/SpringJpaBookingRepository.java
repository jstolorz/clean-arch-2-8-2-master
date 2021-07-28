package com.smalaca.rentalapplication.infrastructure.persistence.jpa.booking;

import com.smalaca.rentalapplication.domain.apartment.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpringJpaBookingRepository extends CrudRepository<Booking, String> {
}
