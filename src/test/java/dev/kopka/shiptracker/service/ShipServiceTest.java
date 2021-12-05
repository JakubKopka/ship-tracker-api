package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.model.ShipClient;
import dev.kopka.shiptracker.client.service.ShipClientService;
import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ShipServiceTest {

    @Mock
    private ShipClientService shipClientService;
    @Mock
    private ShipExtraService shipExtraService;
    @InjectMocks
    private ShipService shipService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all Ships")
    void shouldReturnAllShips() throws TokenNotFoundException, ApiException {
        //given
        var shipClient = new ShipClient();
        shipClient.setName("Ship Tetst #1");
        shipClient.setLat(1.0);
        shipClient.setLon(1.0);
        shipClient.setTimeStamp("2021-12-12T15:00");
        shipClient.setCountry("Poland");
        shipClient.setDestination("Gdańsk");
        shipClient.setEta("2021-12-24T18:00");
        shipClient.setDraught(1.0);
        shipClient.setHeading(1.0);
        shipClient.setShipType(35);
        shipClient.setCallsign("TE3T");
        var shipImg = "https://img.url/ship.png";
        var shipExtra = ShipExtra.builder()
                .shipType(ShipType.MILITARY_OPS)
                .imgUrl(shipImg)
                .build();
        var shipsFormAPI = List.of(
                shipClient
        );
        when(shipClientService.getShipsFormApi()).thenReturn(shipsFormAPI);
        when(shipExtraService.getShipExtraByNumber(shipClient.getShipType())).thenReturn(shipExtra);

        //when
        var listOfShips = shipService.getShips(null);

        //then
        assertNotNull(listOfShips);
        assertEquals(shipsFormAPI.size(), listOfShips.size());
        assertEquals(shipExtra.getShipType().getType(), listOfShips.get(0).getShipType());
        assertEquals(shipExtra.getImgUrl(), listOfShips.get(0).getShipTypeImg());
        assertEquals(shipClient.getLat(), listOfShips.get(0).getLat());
        assertEquals(shipClient.getLon(), listOfShips.get(0).getLon());
        assertEquals(shipClient.getTimeStamp(), listOfShips.get(0).getTime());
        assertEquals(shipClient.getCountry(), listOfShips.get(0).getCountry());
        assertEquals(shipClient.getDestination(), listOfShips.get(0).getDestinationName());
        assertEquals(shipClient.getEta(), listOfShips.get(0).getDestinationEta());
        assertEquals(shipClient.getDraught(), listOfShips.get(0).getDraught());
        assertEquals(shipClient.getHeading(), listOfShips.get(0).getHeading());
        assertEquals(shipClient.getCallsign(), listOfShips.get(0).getCallsign());
    }

    @Test
    @DisplayName("Should return limit Ships")
    void shouldReturnAllShipsWithLimit() throws TokenNotFoundException, ApiException {
        //given
        var limit = 1;
        var shipClient1 = new ShipClient();
        shipClient1.setName("Ship Tetst #1");
        shipClient1.setLat(1.0);
        shipClient1.setLon(1.0);
        shipClient1.setTimeStamp("2021-12-12T15:00");
        shipClient1.setCountry("Poland");
        shipClient1.setDestination("Gdańsk");
        shipClient1.setEta("2021-12-24T18:00");
        shipClient1.setDraught(1.0);
        shipClient1.setHeading(1.0);
        shipClient1.setShipType(35);
        shipClient1.setCallsign("TE3T");
        var shipClient2 = new ShipClient();
        shipClient2.setName("Ship Tetst #2");
        shipClient2.setLat(2.0);
        shipClient2.setLon(2.0);
        shipClient2.setTimeStamp("2021-12-12T15:00");
        shipClient2.setCountry("Spain");
        shipClient2.setDestination("Barcelona");
        shipClient2.setEta("2021-12-24T18:00");
        shipClient2.setDraught(2.0);
        shipClient2.setHeading(2.0);
        shipClient2.setShipType(35);
        shipClient2.setCallsign("TE3T2");
        var shipImg = "https://img.url/ship.png";
        var shipExtra = ShipExtra.builder()
                .shipType(ShipType.MILITARY_OPS)
                .imgUrl(shipImg)
                .build();
        var shipsFormAPI = List.of(
                shipClient1,
                shipClient2
        );
        when(shipClientService.getShipsFormApi()).thenReturn(shipsFormAPI);
        when(shipExtraService.getShipExtraByNumber(anyInt())).thenReturn(shipExtra);

        //when
        var listOfShips = shipService.getShips(limit);

        //then
        assertNotNull(listOfShips);
        assertNotEquals(shipsFormAPI.size(), listOfShips.size());
        assertEquals(limit, listOfShips.size());
    }
}