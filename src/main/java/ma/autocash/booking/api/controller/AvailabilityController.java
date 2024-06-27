package ma.autocash.booking.api.controller;
import ma.autocash.booking.api.entity.Availability;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.provider.AvailabilityProvider;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityProvider availabilityProvider;

    public AvailabilityController(AvailabilityProvider availabilityProvider) {
        this.availabilityProvider = availabilityProvider;
    }

    @PostMapping
    public Availability saveAvailability(@RequestBody Availability availability) {
        return availabilityProvider.saveAvailability(availability);
    }

    @PutMapping("/{id}")
    public Availability updateAvailability(@PathVariable Long id, @RequestBody Availability availability) {
        availability.setId(id);
        return availabilityProvider.updateAvailability(availability);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Long id) {
        availabilityProvider.deleteAvailability(id);
    }

    @GetMapping("/{id}")
    public Availability getAvailabilityById(@PathVariable Long id) {
        return availabilityProvider.getAvailabilityById(id);
    }

    @GetMapping("/expert")
    public List<Availability> getAvailabilitiesByExpertAndDateAndTimeRange(
            @RequestParam Long expertId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime
    ) {
        Expert expert = new Expert();
        expert.setId(expertId);
        return availabilityProvider.getAvailabilitiesByExpertAndDateAndTimeRange(expert, date, startTime, endTime);
    }

    @GetMapping
    public List<Availability> getAllAvailabilities() {
        return availabilityProvider.getAllAvailabilities();
    }
}
