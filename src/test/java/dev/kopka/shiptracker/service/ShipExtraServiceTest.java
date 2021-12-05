package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.repository.ShipExtraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShipExtraServiceTest {
    @Mock
    private ShipExtraRepository shipExtraRepository;

    @InjectMocks
    private ShipExtraService shipExtraService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "ShipType {0}, number {1}")
    @DisplayName("Should return ShipExtra by number")
    @MethodSource("provideShipExtra")
    void shouldReturnShipExtraByNumber(int number, ShipType shipType) {
        //given
        var imgUrl = "https://img.url/ship.png";
        var shipExtra = ShipExtra.builder()
                .shipType(shipType)
                .imgUrl(imgUrl)
                .build();
        when(shipExtraRepository.findByShipType(shipType)).thenReturn(shipExtra);

        //when
        var shipExtraByNumber = shipExtraService.getShipExtraByNumber(number);

        //then
        assertNotNull(shipExtraByNumber);
        assertEquals(shipType, shipExtraByNumber.getShipType());
        assertEquals(imgUrl, shipExtraByNumber.getImgUrl());
    }

    private static Stream<Arguments> provideShipExtra() {
        return Stream.of(
                Arguments.of(20, ShipType.WING_IN_GROUND),
                Arguments.of(30, ShipType.FISHING),
                Arguments.of(33, ShipType.DREDGING_OR_UNDERWATER_OPS),
                Arguments.of(34, ShipType.DIVING_OPS),
                Arguments.of(35, ShipType.MILITARY_OPS),
                Arguments.of(36, ShipType.SAILING),
                Arguments.of(37, ShipType.PLEASURE_CRAFT),
                Arguments.of(40, ShipType.HSC),
                Arguments.of(50, ShipType.PILOT_VESSEL),
                Arguments.of(51, ShipType.SEARCH_AND_RESCUE_VESSEL),
                Arguments.of(52, ShipType.TUG),
                Arguments.of(53, ShipType.PORT_TENDER),
                Arguments.of(54, ShipType.ANTI_POLLUTION_EQUIPMENT),
                Arguments.of(58, ShipType.MEDICAL_TRANSPORT),
                Arguments.of(60, ShipType.PASSENGER),
                Arguments.of(70, ShipType.CARGO),
                Arguments.of(80, ShipType.TANKER),
                Arguments.of(1, ShipType.OTHER_TYPE)
        );
    }

    @Test
    @DisplayName("Should return all ShipExtra objects from DB")
    void shouldReturnAllShipExtra() {
        //given
        var imgUrl = "https://img.url/ship.png";
        var listOfShipsExtra = List.of(
                ShipExtra.builder()
                        .shipType(ShipType.MILITARY_OPS)
                        .imgUrl(imgUrl)
                        .build()
        );
        when(shipExtraRepository.findAll()).thenReturn(listOfShipsExtra);

        //when
        var listOfShipsExtraFromDb = shipExtraService.getAllShipsExtra();

        //then
        assertNotNull(listOfShipsExtraFromDb);
        assertEquals(listOfShipsExtra.size(), listOfShipsExtraFromDb.size());
        assertEquals(listOfShipsExtra.get(0).getShipType(), listOfShipsExtraFromDb.get(0).getShipType());
        assertEquals(listOfShipsExtra.get(0).getImgUrl(), listOfShipsExtraFromDb.get(0).getImgUrl());
    }

    @Test
    @DisplayName("Should update ShipExtra by ShipType")
    void shouldUpdateShipExtra() {
        //given
        var shipType = ShipType.MILITARY_OPS;
        var imgUrl = "https://img.url/ship.png";
        var bodyShipExtra = ShipExtra.builder()
                .imgUrl(imgUrl)
                .build();
        when(shipExtraRepository.findByShipType(shipType)).thenReturn(ShipExtra.builder()
                .shipType(bodyShipExtra.getShipType())
                .imgUrl("https://img.url.shipOldImg.png")
                .build());
        when(shipExtraRepository.save(any())).thenReturn(ShipExtra.builder()
                .shipType(shipType)
                .imgUrl(imgUrl)
                .build());

        //when
        var updatedShipExtra = shipExtraService.updateShipExtra(shipType, bodyShipExtra);

        //then
        assertNotNull(updatedShipExtra);
        assertEquals(shipType, updatedShipExtra.getShipType());
        assertEquals(imgUrl, updatedShipExtra.getImgUrl());
    }

    @Test
    @DisplayName("Should add new ShipExtra when return null from DB")
    void shouldAddShipExtraWhenReturnNullFromDb() {
        //given
        var shipType = ShipType.MILITARY_OPS;
        var imgUrl = "https://img.url/ship.png";
        var bodyShipExtra = ShipExtra.builder()
                .imgUrl(imgUrl)
                .build();
        when(shipExtraRepository.findByShipType(shipType)).thenReturn(null);
        when(shipExtraRepository.save(any())).thenReturn(ShipExtra.builder()
                .shipType(shipType)
                .imgUrl(imgUrl)
                .build());

        //when
        var updatedShipExtra = shipExtraService.updateShipExtra(shipType, bodyShipExtra);

        //then
        assertNotNull(updatedShipExtra);
        assertEquals(shipType, updatedShipExtra.getShipType());
        assertEquals(imgUrl, updatedShipExtra.getImgUrl());
    }
}