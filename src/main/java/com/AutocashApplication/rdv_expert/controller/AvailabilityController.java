package com.AutocashApplication.rdv_expert.controller;
import com.AutocashApplication.rdv_expert.entity.Availability;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.service.AvailabilityService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping
    public Availability saveAvailability(@RequestBody Availability availability) {
        return availabilityService.saveAvailability(availability);
    }

    @PutMapping("/{id}")
    public Availability updateAvailability(@PathVariable Long id, @RequestBody Availability availability) {
        availability.setId(id);
        return availabilityService.updateAvailability(availability);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
    }

    @GetMapping("/{id}")
    public Availability getAvailabilityById(@PathVariable Long id) {
        return availabilityService.getAvailabilityById(id);
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
        return availabilityService.getAvailabilitiesByExpertAndDateAndTimeRange(expert, date, startTime, endTime);
    }

    @GetMapping
    public List<Availability> getAllAvailabilities() {
        return availabilityService.getAllAvailabilities();
    }
}
