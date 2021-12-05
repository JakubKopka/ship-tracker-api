package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.dto.ShipDto;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import dev.kopka.shiptracker.service.ShipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShipControllerTest {
    @Mock
    private ShipService shipService;
    @InjectMocks
    private ShipController shipController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return list of ships")
    void shouldReturnShip() throws TokenNotFoundException, ApiException {
        //given
        var img = "https://xyz.pl/sa.png";
        var name = "Kuba";
        var country = "Poland";
        var latAndLong = 12.34;
        var shipType = ShipType.MILITARY_OPS.getType();
        var ship = ShipDto.builder()
                .name(name)
                .shipTypeImg(img)
                .shipType(shipType)
                .country(country)
                .lat(latAndLong)
                .lon(latAndLong)
                .build();
        when(shipService.getShips(any())).thenReturn(List.of(ship));

        //when
        var response = shipController.getShips(null);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(name, response.getBody().get(0).getName());
        assertEquals(img, response.getBody().get(0).getShipTypeImg());
        assertEquals(shipType, response.getBody().get(0).getShipType());
        assertEquals(country, response.getBody().get(0).getCountry());
        assertEquals(latAndLong, response.getBody().get(0).getLon());
        assertEquals(latAndLong, response.getBody().get(0).getLat());
    }
}