package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.domain.dto.PointDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.BlockedDestinationException;
import dev.kopka.shiptracker.exception.exceptions.DestinationFormatException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class DestinationService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PointService pointService;
    private final Set<String> invalidDestinationNameSet;

    @Autowired
    public DestinationService(PointService pointService, @Value("#{'${list.of.invalid.strings}'.split(',')}") Set<String> invalidDestinationName) {
        this.pointService = pointService;
        this.invalidDestinationNameSet = invalidDestinationName;
    }

    public PointDto getDestinationByName(String destinationName)
            throws DestinationNotFoundException, ApiException, BlockedDestinationException, DestinationFormatException {
        destinationName = prepareString(destinationName);
        if (isBlank(destinationName) || destinationName.length() <= 3) {
            logger.error("Destination name {} have not to be blank or have to length more than 3", destinationName);
            throw new DestinationFormatException("Destination name have not to be blank or have to length more than 3");
        }
        if (!destinationName.chars().allMatch(Character::isLetter)) {
            logger.error("Destination name {} have to contain only letters!", destinationName);
            throw new DestinationFormatException("Destination name have to contain only letters!");
        }
        if (invalidDestinationNameSet.contains(destinationName)) {
            logger.error("The destination name '{}' is blocked! Other blocked names: '{}'",
                    destinationName, String.join(", ", invalidDestinationNameSet));
            throw new BlockedDestinationException(String.format("The destination name '%s' is blocked!",
                    destinationName));
        }
        return pointService.getPointByName(destinationName);
    }

    private String prepareString(String destinationName) {
        return destinationName.replaceAll("\\s+", "").toUpperCase();
    }
}
