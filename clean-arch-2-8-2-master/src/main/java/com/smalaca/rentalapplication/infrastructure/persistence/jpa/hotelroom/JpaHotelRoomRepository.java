package com.smalaca.rentalapplication.infrastructure.persistence.jpa.hotelroom;

import com.smalaca.rentalapplication.domain.hotelroom.HotelRoom;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
class JpaHotelRoomRepository implements HotelRoomRepository {
    private final SpringJpaHotelRoomRepository springJpaHotelRoomRepository;

    JpaHotelRoomRepository(SpringJpaHotelRoomRepository springJpaHotelRoomRepository) {
        this.springJpaHotelRoomRepository = springJpaHotelRoomRepository;
    }

    @Override
    public String save(HotelRoom hotelRoom) {
        return springJpaHotelRoomRepository.save(hotelRoom).id();
    }

    @Override
    public HotelRoom findById(String id) {

        return springJpaHotelRoomRepository.findById(UUID.fromString(id))
                .orElseThrow(()-> new HotelRoomDoesNotExistException(id));
    }
}
