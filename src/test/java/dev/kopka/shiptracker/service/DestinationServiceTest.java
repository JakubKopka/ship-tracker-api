package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.domain.dto.PointDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.BlockedDestinationException;
import dev.kopka.shiptracker.exception.exceptions.DestinationFormatException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DestinationServiceTest {

    @Mock
    private PointService pointService;

    @InjectMocks
    private DestinationService mockedDestinationService;

    private Set<String> invalidDestinationNameSet = Set.of("TEST");

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockedDestinationService = new DestinationService(pointService, invalidDestinationNameSet);
    }

    @Test
    @DisplayName("Should return destination by given name")
    public void shouldReturnDestinationByName()
            throws DestinationNotFoundException, ApiException, DestinationFormatException, BlockedDestinationException {
        //given
        var destinationName = "Warsaw";
        var latAndLong = 123.123;
        var country = "Poland";
        var continent = "Europe";
        var pointDto = PointDto.builder()
                .name(destinationName)
                .latitude(latAndLong)
                .longitude(latAndLong)
                .country(country)
                .continent(continent)
                .build();
        when(pointService.getPointByName(any())).thenReturn(pointDto);

        //when
        var point = mockedDestinationService.getDestinationByName(destinationName);

        //then
        System.out.println(point.getName());
        Assertions.assertNotNull(point);
        Assertions.assertEquals(destinationName, point.getName());
        Assertions.assertEquals(latAndLong, point.getLatitude());
        Assertions.assertEquals(latAndLong, point.getLongitude());
        Assertions.assertEquals(country, point.getCountry());
        Assertions.assertEquals(continent, point.getContinent());
    }

    @Test
    @DisplayName("Should return DestinationFormatException for blank name")
    public void shouldReturnDestinationFormatExceptionForBlankName() throws DestinationFormatException, BlockedDestinationException, DestinationNotFoundException, ApiException {
        //given
        var destinationName = "";
        var exceptionMessage = "Destination name have not to be blank or have to length more than 3";

        //when, then
        DestinationFormatException e =
                Assertions.assertThrows(DestinationFormatException.class, () -> mockedDestinationService.getDestinationByName(destinationName));
        Assertions.assertEquals(exceptionMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should return DestinationFormatException by given name which no contains only letters")
    public void shouldReturnDestinationFormatExceptionForNoOnlyLettersName() {
        //given
        var destinationName = "Wars4w";
        var exceptionMessage = "Destination name have to contain only letters!";

        //when, then
        DestinationFormatException e =
                Assertions.assertThrows(DestinationFormatException.class, () -> mockedDestinationService.getDestinationByName(destinationName));
        Assertions.assertEquals(exceptionMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should return BlockedDestinationException for given blocked name")
    public void shouldReturnBlockedDestinationExceptionForBlockedName() {
        //given
        var destinationName = "TEST";
        var exceptionMessage = "The destination name 'TEST' is blocked!";

        //when, then
        BlockedDestinationException e =
                Assertions.assertThrows(BlockedDestinationException.class, () -> mockedDestinationService.getDestinationByName(destinationName));
        Assertions.assertEquals(exceptionMessage, e.getMessage());
    }
}