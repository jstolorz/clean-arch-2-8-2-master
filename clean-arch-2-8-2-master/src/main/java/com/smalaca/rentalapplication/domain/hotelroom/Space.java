package com.smalaca.rentalapplication.domain.hotelroom;

import javax.persistence.*;

//@Entity
//@Table(name = "HOTEL_ROOM_SPACE")
@Embeddable
class Space {

    private  String name;

    @Embedded
    private  SquareMeter squareMeter;

    private Space() {
    }

    Space(String name, SquareMeter squareMeter) {
        this.name = name;
        this.squareMeter = squareMeter;
    }

}
