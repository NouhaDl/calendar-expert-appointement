package ma.autocash.booking.api.provider;

import jakarta.validation.Valid;
import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;

import java.util.List;

public interface ZoneProvider {

    void saveZone(@Valid Zone zone);

    void updateZone(@Valid Zone zone) throws BusinessException;

    void deleteZone(Long id);

    List<Zone> getAllZones() throws BusinessException;

    Zone getZoneById(Long id) throws BusinessException;
}
