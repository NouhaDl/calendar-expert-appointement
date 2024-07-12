package ma.autocash.booking.api.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/{id}")
    @Operation(summary = "Get an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability found",
                            content = @Content(mediaType = "application/json"))
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
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AvailabilityDto.class)))
            })
    public ResponseEntity<List<AvailabilityDto>> getAvailabilitiesByExpertAndTimeRange(
            @RequestParam Long expertId,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false) LocalTime endTime
    ) {
        List<AvailabilityDto> availabilities = null;
        try {
            availabilities = availabilityService.getAvailabilitiesByExpertAndTimeRange(expertId, startTime, endTime);
        } catch (TechnicalException e) {
            throw new RuntimeException(e);
        }
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
