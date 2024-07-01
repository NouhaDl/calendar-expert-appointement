package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.services.AvailabilityService;
import ma.autocash.booking.api.mapper.AvailabilityMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityController(AvailabilityService availabilityService, AvailabilityMapper availabilityMapper) {
        this.availabilityService = availabilityService;
        this.availabilityMapper = availabilityMapper;
    }

    @PostMapping
    public AvailabilityDto saveAvailability(@RequestBody AvailabilityDto availabilityDto) {
        return availabilityService.saveAvailability(availabilityDto);
    }

    @PutMapping("/{id}")
    public AvailabilityDto updateAvailability(@PathVariable Long id, @RequestBody AvailabilityDto availabilityDto) {
        return availabilityService.updateAvailability(id, availabilityDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
    }

    @GetMapping("/{id}")
    public AvailabilityDto getAvailabilityById(@PathVariable Long id) {
        return availabilityService.getAvailabilityById(id);
    }

    @GetMapping("/expert")
    public List<AvailabilityDto> getAvailabilitiesByExpertAndDateAndTimeRange(
            @RequestParam Long expertId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime
    ) {
        return availabilityService.getAvailabilitiesByExpertAndDateAndTimeRange(expertId, date, startTime, endTime);
    }

    @GetMapping
    public List<AvailabilityDto> getAllAvailabilities() {
        return availabilityService.getAllAvailabilities();
    }
}
