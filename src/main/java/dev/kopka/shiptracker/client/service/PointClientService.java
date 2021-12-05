package dev.kopka.shiptracker.client.service;

import dev.kopka.shiptracker.client.model.PointClient;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PointClientService {

    private final DestinationClientService destinationClientService;

    public PointClientService(DestinationClientService destinationClientService) {
        this.destinationClientService = destinationClientService;
    }

    public PointClient getPointByName(String destinationName) throws ApiException, DestinationNotFoundException {
        return destinationClientService.getDestinationFromAPI(destinationName);
    }
}
