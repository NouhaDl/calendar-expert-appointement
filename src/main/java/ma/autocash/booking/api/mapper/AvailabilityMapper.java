package ma.autocash.booking.api.mapper;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    @Mappings({
            @Mapping(source = "expert", target = "expertId", qualifiedByName = "extractExpertId")
    })
    AvailabilityDto toDto(Availability availability);

    @Mappings({
            @Mapping(target = "expert", source = "expertId", qualifiedByName = "mapExpertIdToExpert")
    })
    Availability toEntity(AvailabilityDto availabilityDto);
    @Named("extractExpertId")
    default Long extractExpertId(Expert expert) {
        return expert != null ? expert.getId() : null;
    }
    @Named("mapExpertIdToExpert")
    default Expert mapExpertIdToExpert(Long expertId) {
        if (expertId == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(expertId);
        return expert;
    }
}
