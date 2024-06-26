package com.AutocashApplication.rdv_expert.service;

import com.AutocashApplication.rdv_expert.entity.Zone;
import com.AutocashApplication.rdv_expert.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone saveZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public Zone updateZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id).orElse(null);
    }
}
