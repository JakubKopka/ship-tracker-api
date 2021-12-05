package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.service.PointClientService;
import dev.kopka.shiptracker.domain.dto.PointDto;
import dev.kopka.shiptracker.domain.model.Point;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import dev.kopka.shiptracker.repository.PointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PointClientService pointClientService;
    private final PointRepository pointRepository;

    @Autowired
    public PointService(PointClientService pointClientService, PointRepository pointRepository) {
        this.pointClientService = pointClientService;
        this.pointRepository = pointRepository;
    }

    public PointDto getPointByName(String destinationName) throws DestinationNotFoundException, ApiException {
        var pointDb = pointRepository.findByName(destinationName);
        if (pointDb != null) {
            logger.info("Found destination {} in DB", destinationName);
            return PointDto.builder()
                    .name(pointDb.getName())
                    .latitude(pointDb.getLatitude())
                    .longitude(pointDb.getLongitude())
                    .country(pointDb.getCountry())
                    .continent(pointDb.getContinent())
                    .build();
        }
        var pointApi = getPointFromApi(destinationName);
        logger.info("Found destination {} in API", destinationName);
        addNewPointToDb(pointApi);
        return pointApi;
    }

    private void addNewPointToDb(PointDto pointApi) {
        try {
            pointRepository.save(Point.builder()
                    .name(pointApi.getName().toUpperCase())
                    .latitude(pointApi.getLatitude())
                    .longitude(pointApi.getLongitude())
                    .country(pointApi.getCountry())
                    .continent(pointApi.getContinent())
                    .build());
        } catch (Exception e) {
            logger.error("Error during save new destination {} to DB", pointApi.getName());
        }
    }

    private PointDto getPointFromApi(String destinationName) throws DestinationNotFoundException, ApiException {
        var point = pointClientService.getPointByName(destinationName);
        return PointDto.builder().name(point.getName())
                .continent(point.getContinent())
                .country(point.getCountry())
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .build();
    }
}
