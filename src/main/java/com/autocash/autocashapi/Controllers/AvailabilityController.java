package com.autocash.autocashapi.Controllers;
import com.autocash.autocashapi.Dtos.AvailabilityDto;
import com.autocash.autocashapi.Services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<AvailabilityDto> createAvailability(@RequestBody AvailabilityDto availabilityDto) {
        AvailabilityDto createdAvailability = availabilityService.saveAvailability(availabilityDto);
        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityDto>> getAllAvailabilities() {
        List<AvailabilityDto> availabilities = availabilityService.findAllAvailabilities();
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDto> getAvailabilityById(@PathVariable Long id) {
        AvailabilityDto availabilityDto = availabilityService.findAvailabilityById(id);
        return new ResponseEntity<>(availabilityDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityDto> updateAvailability(@PathVariable Long id, @RequestBody AvailabilityDto availabilityDto) {
        AvailabilityDto updatedAvailability = availabilityService.updateAvailability(id, availabilityDto);
        return new ResponseEntity<>(updatedAvailability, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        if (availabilityService.deleteAvailability(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add other controller methods as needed

}
