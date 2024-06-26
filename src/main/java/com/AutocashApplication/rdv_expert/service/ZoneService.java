package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Zone;

import java.util.List;

public interface ZoneService {
    Zone saveZone(Zone zone);
    Zone updateZone(Zone zone);
    void deleteZone(Long id);
    List<Zone> getAllZones();
    Zone getZoneById(Long id);
}
