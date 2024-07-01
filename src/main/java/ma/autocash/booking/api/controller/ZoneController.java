package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.services.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public ZoneDto saveZone(@RequestBody ZoneDto zoneDto) {
        return zoneService.saveZone(zoneDto);
    }

    @PutMapping("/{id}")
    public ZoneDto updateZone(@PathVariable Long id, @RequestBody ZoneDto zoneDto) {
        return zoneService.updateZone(id, zoneDto);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
    }

    @GetMapping
    public List<ZoneDto> getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{id}")
    public ZoneDto getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }
}
