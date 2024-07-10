package ma.autocash.booking.api.provider.impl;

import lombok.SneakyThrows;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.provider.ZoneProvider;
import ma.autocash.booking.api.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneProviderImpl implements ZoneProvider {

    private final ZoneRepository zoneRepository;

    public ZoneProviderImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @SneakyThrows
    @Override
    public Zone saveZone(Zone zone) throws TechnicalException {
        try {
            return zoneRepository.save(zone);
        } catch (Exception e) {
            throw new TechnicalException("Error saving zone", e);
        }
    }

    @SneakyThrows
    @Override
    public Zone updateZone(Zone zone) throws BusinessException, TechnicalException {
        try {
            if (zoneRepository.existsById(zone.getId())) {
                return zoneRepository.save(zone);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("zone.update.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new TechnicalException("Error updating zone", e);
        }
    }

    @SneakyThrows
    @Override
    public void deleteZone(Long id) throws BusinessException, TechnicalException {
        try {
            if (zoneRepository.existsById(id)) {
                zoneRepository.deleteById(id);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("zone.delete.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new TechnicalException("Error deleting zone", e);
        }
    }

    @SneakyThrows
    @Override
    public List<Zone> getAllZones() throws TechnicalException {
        try {
            return zoneRepository.findAll();
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zones", e);
        }
    }

    @SneakyThrows
    @Override
    public Zone getZoneById(Long id) throws BusinessException, TechnicalException {
        try {
            return zoneRepository.findById(id).orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("zone.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new TechnicalException("Error retrieving zone by id", e);
        }
    }
}
