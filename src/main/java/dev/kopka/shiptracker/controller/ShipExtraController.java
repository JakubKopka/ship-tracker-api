package dev.kopka.shiptracker.controller;

import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import dev.kopka.shiptracker.service.ShipExtraService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/ship/extra")
@CrossOrigin
public class ShipExtraController {

    private final ShipExtraService shipExtraService;

    public ShipExtraController(ShipExtraService shipExtraService) {
        this.shipExtraService = shipExtraService;
    }

    @ApiOperation(
            value = "Get all extra ships information like: type and imgUrl",
            nickname = "Get ships extra data")
    @GetMapping("all")
    @ResponseBody
    public ResponseEntity<List<ShipExtra>> getAllShipsExtra() {
        return new ResponseEntity<>(shipExtraService.getAllShipsExtra(), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Update especially imgUrl for selected ship type",
            nickname = "Update Ship extra data")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "shipType",
                    value = "one of ship types",
                    required = true,
                    dataType = "ShipType",
                    paramType = "path",
                    defaultValue = "CARGO")
    })
    @PatchMapping("{shipType}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShipExtra> updateShipExtra(@PathVariable("shipType") ShipType shipType, @RequestBody ShipExtra shipExtra) {
        return new ResponseEntity<>(shipExtraService.updateShipExtra(shipType, shipExtra), HttpStatus.OK);
    }
}
