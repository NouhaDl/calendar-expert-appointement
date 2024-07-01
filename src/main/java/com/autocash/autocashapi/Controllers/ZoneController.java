package com.autocash.autocashapi.Controllers;
import com.autocash.autocashapi.Dtos.ZoneDto;
import com.autocash.autocashapi.Services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(@RequestBody ZoneDto zoneDto) {
        ZoneDto createdZone = zoneService.saveZone(zoneDto);
        return new ResponseEntity<>(createdZone, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ZoneDto>> getAllZones() {
        List<ZoneDto> zones = zoneService.findAllZones();
        return new ResponseEntity<>(zones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDto> getZoneById(@PathVariable Long id) {
        ZoneDto zoneDto = zoneService.findZoneById(id);
        return new ResponseEntity<>(zoneDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDto> updateZone(@PathVariable Long id, @RequestBody ZoneDto zoneDto) {
        ZoneDto updatedZone = zoneService.updateZone(id, zoneDto);
        return new ResponseEntity<>(updatedZone, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        boolean deleted = zoneService.deleteZone(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add other controller methods as needed
}

