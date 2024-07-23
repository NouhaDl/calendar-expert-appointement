package ma.autocash.booking.api.provider.impl;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.KeyValueErrorImpl;
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
    public Zone saveZone(Zone zone) throws BusinessException {
        try {
            return zoneRepository.save(zone);
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("zone.cannot.be saved", 404, 404));
        }
    }
    @Override
    public Zone updateZone(Zone zone) throws BusinessException {
        try {
            if (zoneRepository.existsById(zone.getId())) {
                return zoneRepository.save(zone);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("zone.update.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("zone.cannot.be updated", 404, 404));
        }
    }
    @Override
    public void deleteZone(Long id) throws BusinessException {
        try {
            if (zoneRepository.existsById(id)) {
                zoneRepository.deleteById(id);
            } else {
                throw new BusinessException(new KeyValueErrorImpl("zone.delete.notfound", 404, 404));
            }
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("zone.delete.notfound", 404, 404));
        }
    }
    @Override
    public List<Zone> getAllZones() throws BusinessException {
        try {
            return zoneRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("zone.get.notfound", 404, 404));
        }
    }
    @Override
    public Zone getZoneById(Long id) throws BusinessException {
        try {
            return zoneRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(new KeyValueErrorImpl("zone.get.notfound", 404, 404)));
        } catch (Exception e) {
            throw new BusinessException(new KeyValueErrorImpl("zone.get.notfound", 404, 404));
        }
    }
}
