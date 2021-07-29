package com.smalaca.rentalapplication.infrastructure.persistence.jpa.hotelroom;

import com.google.common.collect.ImmutableMap;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoom;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomAssertion;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomFactory;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JpaHotelRoomRepositoryIntegrationTest {

    private String hotelId = "1234";
    private int number = 10;
    private Map<String, Double> spacesDefinition = ImmutableMap.of("Bed Room",12.9,"Kitchen",11.9);
    private String description = "nice place to rest";

    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    private final HotelRoomFactory hotelRoomFactory = new HotelRoomFactory();

    @Test
    void shouldThrowExceptionWhenHotelRoomDoesNotExist() {
        String nonExistingHotelRoomId = UUID.randomUUID().toString();

        HotelRoomDoesNotExistException actual = assertThrows(HotelRoomDoesNotExistException.class, () ->{
           hotelRoomRepository.findById(nonExistingHotelRoomId);
        });

        assertThat(actual).hasMessage("hotelRoom with id " + nonExistingHotelRoomId + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingHotelRoom(){
         HotelRoom hotelRoom = createHotelRoom();
         String existingId = hotelRoomRepository.save(hotelRoom);

         HotelRoom actual = hotelRoomRepository.findById(existingId);

        HotelRoomAssertion.assertThat(actual)
                .hasHotelIdEqualsTo(hotelId)
                .hasNumberEqualsTo(number)
                .hasSpacesDescriptionEqualsTo(spacesDefinition)
                .hasDescriptionEqualsTo(description);
    }

    @Test
    @Transactional
    void shouldReturnExistingHotelRoomWeWant(){
        HotelRoom hotelRoom1 = hotelRoomFactory.create("333",23,ImmutableMap.of("bad",23.9),"home1");
        hotelRoomRepository.save(hotelRoom1);

        HotelRoom hotelRoom2 = hotelRoomFactory.create("344",11,ImmutableMap.of("kitchen",24.9),"home2");
        hotelRoomRepository.save(hotelRoom2);

        HotelRoom hotelRoom3 = createHotelRoom();
        String Id = hotelRoomRepository.save(hotelRoom3);

        HotelRoom hotelRoom4 = hotelRoomFactory.create("333",23,ImmutableMap.of("bad",23.9),"home");
        hotelRoomRepository.save(hotelRoom4);

        HotelRoom actual = hotelRoomRepository.findById(Id);

        HotelRoomAssertion.assertThat(actual)
                .hasHotelIdEqualsTo(hotelId)
                .hasNumberEqualsTo(number)
                .hasSpacesDescriptionEqualsTo(spacesDefinition)
                .hasDescriptionEqualsTo(description);

    }

    private HotelRoom createHotelRoom() {
        return hotelRoomFactory.create(hotelId, number, spacesDefinition, description);
    }
}