package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;

import java.util.List;

public interface ZoneProvider {
    Zone saveZone(Zone zone) throws TechnicalException;

    Zone updateZone(Zone zone) throws BusinessException, TechnicalException;

    void deleteZone(Long id) throws BusinessException, TechnicalException;

    List<Zone> getAllZones() throws TechnicalException;

    Zone getZoneById(Long id) throws BusinessException, TechnicalException;
}
