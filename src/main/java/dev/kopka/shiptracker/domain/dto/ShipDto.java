package dev.kopka.shiptracker.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShipDto {
    private String name;
    private double lat;
    private double lon;
    private String time;
    private String country;
    private String destinationName;
    private String destinationEta;
    private double heading;
    private double draught;
    private String shipType;
    private String shipTypeImg;
    private String callsign;
}
