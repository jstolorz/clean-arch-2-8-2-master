package com.smalaca.rentalapplication.infrastructure.persistence.jpa.hotelroom;

public class HotelRoomDoesNotExistException extends RuntimeException{
    HotelRoomDoesNotExistException(String id) {
        super("hotelRoom with id " + id + " does not exist");
    }
}
