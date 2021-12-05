package dev.kopka.shiptracker.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointDto {
    private String name;
    private Double latitude;
    private Double longitude;
    private String country;
    private String continent;
}
