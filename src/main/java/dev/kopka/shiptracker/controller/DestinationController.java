package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.dto.PointDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.BlockedDestinationException;
import dev.kopka.shiptracker.exception.exceptions.DestinationFormatException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import dev.kopka.shiptracker.service.DestinationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/destination")
@CrossOrigin
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @ApiOperation(
            value = "Find destination latitude, longitude, country and continent by name",
            nickname = "Get destination by name")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "destinationName",
                    value = "Destination name",
                    required = true,
                    dataType = "String",
                    paramType = "path",
                    defaultValue = "Warsaw")
    })
    @GetMapping("{destinationName}")
    @ResponseBody
    public ResponseEntity<PointDto> getDestinationByName(@PathVariable("destinationName") String destinationName)
            throws DestinationNotFoundException, ApiException, DestinationFormatException, BlockedDestinationException {
        return new ResponseEntity<>(destinationService.getDestinationByName(destinationName), HttpStatus.OK);
    }
}
