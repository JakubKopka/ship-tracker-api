package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.model.PointClient;
import dev.kopka.shiptracker.client.service.PointClientService;
import dev.kopka.shiptracker.domain.model.Point;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import dev.kopka.shiptracker.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PointServiceTest {

    @Mock
    private PointClientService pointClientService;
    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService mockedPointService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return Point by given name form DB")
    void shouldReturnPointFromDb() throws DestinationNotFoundException, ApiException {
        //given
        var destinationName = "Warsaw";
        var latAndLong = 123.123;
        var country = "Poland";
        var continent = "Europe";
        var point = Point.builder()
                .name(destinationName)
                .latitude(latAndLong)
                .longitude(latAndLong)
                .country(country)
                .continent(continent)
                .build();
        when(pointRepository.findByName(any())).thenReturn(point);
        //when
        var pointDto = mockedPointService.getPointByName(destinationName);

        //then
        assertNotNull(pointDto);
        assertEquals(point.getName(), pointDto.getName());
        assertEquals(point.getLatitude(), pointDto.getLatitude());
        assertEquals(point.getLongitude(), pointDto.getLongitude());
        assertEquals(point.getCountry(), pointDto.getCountry());
        assertEquals(point.getContinent(), pointDto.getContinent());
    }

    @Test
    @DisplayName("Should return Point by given name form API")
    void shouldReturnPointFromApi() throws ApiException, DestinationNotFoundException {
        //given
        var destinationName = "Warsaw";
        var latAndLong = 123.123;
        var country = "Poland";
        var continent = "Europe";
        var pointClient = new PointClient();
        pointClient.setName(destinationName);
        pointClient.setLatitude(latAndLong);
        pointClient.setLongitude(latAndLong);
        pointClient.setCountry(country);
        pointClient.setContinent(continent);
        when(pointClientService.getPointByName(any())).thenReturn(pointClient);
        //when
        var pointDto = mockedPointService.getPointByName(destinationName);

        //then
        assertNotNull(pointDto);
        assertEquals(destinationName, pointDto.getName());
        assertEquals(latAndLong, pointDto.getLatitude());
        assertEquals(latAndLong, pointDto.getLongitude());
        assertEquals(country, pointDto.getCountry());
        assertEquals(continent, pointDto.getContinent());
    }

}