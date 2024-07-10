package ma.autocash.booking.api.controller;

import lombok.SneakyThrows;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.services.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @SneakyThrows
    @PostMapping
    @Operation(summary = "Create a new Availability",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability created successfully",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<AvailabilityDto> saveAvailability(@Valid @RequestBody AvailabilityDto availabilityDto) throws TechnicalException {
        AvailabilityDto savedAvailability = availabilityService.saveAvailability(availabilityDto);
        return ResponseEntity.ok(savedAvailability);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Availability not found")
            })
    public ResponseEntity<AvailabilityDto> updateAvailability(@PathVariable Long id, @Valid @RequestBody AvailabilityDto availabilityDto) throws BusinessException, TechnicalException {
        AvailabilityDto updatedAvailability = availabilityService.updateAvailability(id, availabilityDto);
        return ResponseEntity.ok(updatedAvailability);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Availability deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Availability not found")
            })
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) throws TechnicalException {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @Operation(summary = "Get an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Availability not found")
            })
    public ResponseEntity<AvailabilityDto> getAvailabilityById(@PathVariable Long id) throws BusinessException, TechnicalException {
        AvailabilityDto availability = availabilityService.getAvailabilityById(id);
        return availability != null ?
                ResponseEntity.ok(availability) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/expert")
    @Operation(summary = "Get Availabilities by Expert ID and Time Range",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availabilities found",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<AvailabilityDto>> getAvailabilitiesByExpertAndDateAndTimeRange(
            @RequestParam Long expertId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime
    ) throws TechnicalException {
        List<AvailabilityDto> availabilities = availabilityService.getAvailabilitiesByExpertAndDateAndTimeRange(expertId, date, startTime, endTime);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping
    @Operation(summary = "Get all Availabilities",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availabilities found",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<AvailabilityDto>> getAllAvailabilities() throws TechnicalException {
        List<AvailabilityDto> availabilities = availabilityService.getAllAvailabilities();
        return ResponseEntity.ok(availabilities);
    }
}
