package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.model.ShipClient;
import dev.kopka.shiptracker.client.service.ShipClientService;
import dev.kopka.shiptracker.domain.dto.ShipDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipClientService shipClientService;
    private final ShipExtraService shipExtraService;

    @Autowired
    public ShipService(ShipClientService shipClientService, ShipExtraService shipExtraService) {
        this.shipClientService = shipClientService;
        this.shipExtraService = shipExtraService;
    }

    public List<ShipDto> getShips(Integer limit) throws TokenNotFoundException, ApiException {
        var shipsFromApi = shipClientService.getShipsFormApi();
        var ships = shipsFromApi.stream()
                .map(this::mapShipToShipDto)
                .collect(Collectors.toList());
        if (limit != null) {
            return ships.stream().limit(limit).collect(Collectors.toList());
        }
        return ships;
    }

    private ShipDto mapShipToShipDto(ShipClient ship) {
        var draught = ship.getDraught() == null ? 0.0 : ship.getDraught();
        var heading = ship.getHeading() == null ? 0.0 : Double.parseDouble(String.valueOf(ship.getHeading()));
        var shipType = shipExtraService.getShipExtraByNumber(ship.getShipType());
        var destinationEta = ship.getEta() == null ? "No information" : ship.getEta();
        return ShipDto.builder()
                .name(ship.getName())
                .lat(ship.getLat())
                .lon(ship.getLon())
                .time(ship.getTimeStamp())
                .country(ship.getCountry())
                .destinationName(ship.getDestination())
                .destinationEta(destinationEta)
                .heading(heading)
                .draught(draught)
                .shipType(shipType.getShipType().getType())
                .shipTypeImg(shipType.getImgUrl())
                .callsign(ship.getCallsign())
                .build();
    }
}
