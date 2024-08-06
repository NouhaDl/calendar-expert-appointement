package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping
    public void createAvailability(@RequestBody AvailabilityDto availabilityDto) throws BusinessException {
        availabilityService.addExpertAvailability(availabilityDto);
    }

    @PutMapping("/{id}")
    public void updateAvailability(@PathVariable Long id, @RequestBody AvailabilityDto availabilityDto) throws BusinessException {
        availabilityDto.setId(id);
        availabilityService.updateAvailability(availabilityDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Long id) throws BusinessException {
        availabilityService.deleteAvailability(id);
    }

    @GetMapping("/{id}")
    public AvailabilityDto getAvailabilityById(@PathVariable Long id) throws BusinessException {
       return availabilityService.getAvailabilityById(id);
    }

    @GetMapping
    public List<AvailabilityDto> getAvailabilitiesByExpertAndDate(
            @RequestParam Long expertId,
            @RequestParam LocalDate date) throws BusinessException {
        return availabilityService.getAvailabilitiesByExpertAndDate(expertId, date);
    }

    @GetMapping("/all")
    public List<AvailabilityDto> getAllAvailabilities() throws BusinessException {
        return availabilityService.getAllAvailabilities();
    }

    @PostMapping("/from-booking")
    public AvailabilityDto createAvailabilityFromBooking(@RequestBody BookingDto bookingDto) throws BusinessException {
        return availabilityService.createAvailabilityFromBooking(bookingDto);
    }
}
