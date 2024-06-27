package ma.autocash.booking.api.controller;


import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.provider.ZoneProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneProvider zoneProvider;

    public ZoneController(ZoneProvider zoneProvider) {
        this.zoneProvider = zoneProvider;
    }

    @PostMapping
    public Zone saveZone(@RequestBody Zone zone) {
        return zoneProvider.saveZone(zone);
    }

    @PutMapping("/{id}")
    public Zone updateZone(@PathVariable Long id, @RequestBody Zone zone) {
        // Assuming the zone object already has the ID set
        zone.setId(id);
        return zoneProvider.updateZone(zone);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneProvider.deleteZone(id);
    }
    @GetMapping
    public List<Zone> getAllZones() {
        return zoneProvider.getAllZones();
    }

    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable Long id) {
        return zoneProvider.getZoneById(id);
    }


}

