package com.smalaca.rentalapplication.domain.hotelroom;

import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class HotelRoomAssertion {
    private HotelRoom actual;

    public HotelRoomAssertion(final HotelRoom actual) {
        this.actual = actual;
    }

    public static HotelRoomAssertion assertThat(final HotelRoom actual) {
        return new HotelRoomAssertion(actual);
    }

    public HotelRoomAssertion hasHotelIdEqualsTo(final String hotelId) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelId", hotelId);
        return this;
    }

    public HotelRoomAssertion hasNumberEqualsTo(final int number) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("number", number);
        return this;
    }

    public HotelRoomAssertion hasSpacesDescriptionEqualsTo(final Map<String, Double> spacesDefinition) {
        Assertions.assertThat(actual).extracting("spaces").satisfies(spacesActual -> {
            List<Space> spaces = (List<Space>)spacesActual;
            Assertions.assertThat(spaces).hasSize(spacesDefinition.size());

            spacesDefinition.forEach((name,squareMeter) -> {
                Assertions.assertThat(spaces).anySatisfy(hasSpaceThat(name, squareMeter));
            });

        });

        return this;
    }

    private Consumer<Space> hasSpaceThat(final String name, final Double squareMeter) {
        return space -> Assertions.assertThat(space)
                .hasFieldOrPropertyWithValue("name",name)
                .hasFieldOrPropertyWithValue("squareMeter.value",squareMeter);
    }

    public HotelRoomAssertion hasDescriptionEqualsTo(final String description) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("description", description);
        return this;
    }
}
