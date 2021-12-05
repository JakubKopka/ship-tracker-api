package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.dto.PointDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.BlockedDestinationException;
import dev.kopka.shiptracker.exception.exceptions.DestinationFormatException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import dev.kopka.shiptracker.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DestinationControllerTest {

    @Mock
    private DestinationService destinationService;
    @InjectMocks
    private DestinationController destinationController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return destination by given name")
    void shouldReturnDestinationByName()
            throws DestinationFormatException, BlockedDestinationException, DestinationNotFoundException, ApiException {
        //given
        var destinationName = "Warsaw";
        var lotAndLang = 12.234;
        var country = "Poland";
        var continent = "Europe";
        var pointDto = PointDto.builder()
                .name(destinationName)
                .latitude(lotAndLang)
                .longitude(lotAndLang)
                .country(country)
                .continent(continent)
                .build();
        when(destinationService.getDestinationByName(any())).thenReturn(pointDto);

        //when
        var response = destinationController.getDestinationByName(destinationName);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(destinationName, response.getBody().getName());
        assertEquals(lotAndLang, response.getBody().getLongitude());
        assertEquals(lotAndLang, response.getBody().getLatitude());
        assertEquals(continent, response.getBody().getContinent());
        assertEquals(country, response.getBody().getCountry());
    }
}