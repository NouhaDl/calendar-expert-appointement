package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneProviderImpl implements ZoneProvider {

    private final ZoneRepository zoneRepository;

    public ZoneProviderImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone saveZone(Zone zone) {
        try {
            return zoneRepository.save(zone);
        } catch (Exception e) {
            throw new TechnicalException("Error saving zone", e);
        }
    }

    @Override
    public Zone updateZone(Zone zone) {
        try {
            if (zoneRepository.existsById(zone.getId())) {
                return zoneRepository.save(zone);
            } else {
                throw new BusinessException("Zone not found for update");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error updating zone", e);
        }
    }

    @Override
    public void deleteZone(Long id) {
        try {
            if (zoneRepository.existsById(id)) {
                zoneRepository.deleteById(id);
            } else {
                throw new BusinessException("Zone not found for deletion");
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting zone", e);
        }
    }

    @Override
    public List<Zone> getAllZones() {
        try {
            return zoneRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zones", e);
        }
    }

    @Override
    public Zone getZoneById(Long id) {
        try {
            return zoneRepository.findById(id).orElseThrow(() -> new BusinessException("Zone not found"));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zone by id", e);
        }
    }
}
