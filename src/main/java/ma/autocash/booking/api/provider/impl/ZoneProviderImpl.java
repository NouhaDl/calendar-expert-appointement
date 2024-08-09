package ma.autocash.booking.api.provider.impl;

import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.ApiErrors;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ZoneProviderImpl implements ZoneProvider {

    private final ZoneRepository zoneRepository;

    public ZoneProviderImpl(ZoneRepository zoneRepository) {

        this.zoneRepository = zoneRepository;
    }

    @Override
    public void saveZone(Zone zone) {

        zoneRepository.save(zone);
    }

    @Override
    public void updateZone(Zone zone) throws BusinessException {
        if (zoneRepository.existsById(zone.getId())) {
            zoneRepository.save(zone);
        } else {
            throw new BusinessException(ApiErrors.ZONE_NOT_FOUND);
        }
    }

    @Override
    public void deleteZone(Long id)  {
       zoneRepository.existsById(id) ;
       zoneRepository.deleteById(id);

    }

    @Override
    public List<Zone> getAllZones() throws BusinessException {
        List<Zone> zones = zoneRepository.findAll();
        if (zones.isEmpty()) {
            throw new BusinessException(ApiErrors.ZONE_NOT_FOUND);
        }
        return zones;
    }



    @Override
    public Zone getZoneById(Long id) throws BusinessException {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiErrors.ZONE_NOT_FOUND));
    }
}
