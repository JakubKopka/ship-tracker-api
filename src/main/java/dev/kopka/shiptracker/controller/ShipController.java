package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.dto.ShipDto;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import dev.kopka.shiptracker.service.ShipService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @ApiOperation(
            value = "Get all ships with optional limit",
            nickname = "Get ships")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "limit",
                    value = "limit",
                    dataType = "Integer",
                    paramType = "param",
                    defaultValue = "100")
    })
    @GetMapping("ship")
    @ResponseBody
    public ResponseEntity<List<ShipDto>> getShips(@RequestParam(value = "limit", required = false) Integer limit)
            throws TokenNotFoundException, ApiException {
        return new ResponseEntity<>(shipService.getShips(limit), HttpStatus.OK);
    }
}
