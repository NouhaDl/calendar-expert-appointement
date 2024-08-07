package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.dto.AvailabilityResponseDto;
import ma.autocash.booking.api.dto.BookingDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
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
                    @ApiResponse(responseCode = "201", description = "Availability created successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<AvailabilityResponseDto> createAvailability(@Valid @RequestBody AvailabilityDto availabilityDto) throws BusinessException {
        availabilityService.addExpertAvailability(availabilityDto);
        AvailabilityResponseDto createdAvailability = availabilityService.getAvailabilityById(availabilityDto.getId());
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Availability not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<AvailabilityResponseDto> updateAvailability(@PathVariable Long id, @Valid @RequestBody AvailabilityDto availabilityDto) throws BusinessException {
        availabilityDto.setId(id);
        availabilityService.updateAvailability(availabilityDto);
        AvailabilityResponseDto updatedAvailability = availabilityService.getAvailabilityById(id);
        return  ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Availability deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Availability not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) throws BusinessException {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availability found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Availability not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<AvailabilityResponseDto> getAvailabilityById(@PathVariable Long id) throws BusinessException {
        AvailabilityResponseDto availability = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(availability);
    }

    @GetMapping
    @Operation(summary = "Get Availabilities by Expert and Date",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availabilities found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No availabilities found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<AvailabilityResponseDto>> getAvailabilitiesByExpertAndDate(
            @RequestParam Long expertId,
            @RequestParam LocalDate date) throws BusinessException {
        List<AvailabilityResponseDto> availabilities = availabilityService.getAvailabilitiesByExpertAndDate(expertId, date);
        return availabilities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availabilities);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Availabilities",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availabilities found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No availabilities found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<AvailabilityResponseDto>> getAllAvailabilities() throws BusinessException {
        List<AvailabilityResponseDto> availabilities = availabilityService.getAllAvailabilities();
        return availabilities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availabilities);
    }

    @PostMapping("/from-booking")
    @Operation(summary = "Create Availability from Booking",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Availability created from booking successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<AvailabilityDto> createAvailabilityFromBooking(@Valid @RequestBody BookingDto bookingDto) throws BusinessException {
        AvailabilityDto createdAvailability = availabilityService.createAvailabilityFromBooking(bookingDto);
        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }
}
