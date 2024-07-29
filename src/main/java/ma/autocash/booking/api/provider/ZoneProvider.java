package ma.autocash.booking.api.provider;

import ma.autocash.booking.api.entity.Zone;
import ma.autocash.booking.api.exception.BusinessException;
import java.util.List;

public interface ZoneProvider {
    void saveZone(Zone zone) throws BusinessException;
    void updateZone(Zone zone) throws BusinessException;
    void deleteZone(Long id) ;
    List<Zone> getAllZones() throws BusinessException;
    List<Zone> getZonesByIds(List<Long> ids) throws BusinessException;
    Zone getZoneById(Long id) throws BusinessException;
}
