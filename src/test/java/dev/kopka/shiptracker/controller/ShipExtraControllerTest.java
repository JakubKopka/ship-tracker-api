package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.service.ShipExtraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShipExtraControllerTest {

    @Mock
    private ShipExtraService shipExtraService;
    @InjectMocks
    private ShipExtraController shipExtraController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all ships extra data")
    void shouldReturnAllShipsExtra() {
        //given
        var imgUrl = "https://xyz.pl/sa.png";
        var shipType = ShipType.MILITARY_OPS;
        var ship = ShipExtra.builder()
                .shipType(shipType)
                .imgUrl(imgUrl)
                .build();
        when(shipExtraService.getAllShipsExtra()).thenReturn(List.of(ship));

        //when
        var response = shipExtraController.getAllShipsExtra();

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(shipType, response.getBody().get(0).getShipType());
        assertEquals(imgUrl, response.getBody().get(0).getImgUrl());
    }

    @Test
    @DisplayName("Should update ShipExtra by ShipType")
    void shouldUpdateShipExtraByShipType() {
        //given
        var imgUrl = "https://xyz.pl/sa.png";
        var newImgUrl = "https://new.img/url.png";
        var shipType = ShipType.MILITARY_OPS;
        var shipExtraBody = ShipExtra.builder()
                .shipType(shipType)
                .imgUrl(newImgUrl)
                .build();
        when(shipExtraService.updateShipExtra(shipType, shipExtraBody)).thenReturn(shipExtraBody);

        //when
        var response = shipExtraController.updateShipExtra(shipType, shipExtraBody);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(shipType, response.getBody().getShipType());
        assertEquals(newImgUrl, response.getBody().getImgUrl());
    }
}