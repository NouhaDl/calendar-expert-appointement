package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Zone;

import java.util.List;

public interface ZoneProvider {
    Zone saveZone(Zone zone);
    Zone updateZone(Zone zone);
    void deleteZone(Long id);
    List<Zone> getAllZones();
    Zone getZoneById(Long id);
}
