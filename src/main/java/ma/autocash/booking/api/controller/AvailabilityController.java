package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.AvailabilityDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> addExpertAvailability(@Valid @RequestBody AvailabilityDto availabilityDto)
            throws  BusinessException {
        availabilityService.addExpertAvailability(availabilityDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<Void> updateAvailability(@Valid @RequestBody AvailabilityDto availabilityDto)
            throws BusinessException {
        availabilityService.updateAvailability(availabilityDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Availability by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Availability deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Availability not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id)  {
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
    public ResponseEntity<AvailabilityDto> getAvailabilityById(@PathVariable Long id) throws BusinessException {
        AvailabilityDto availability = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(availability);
    }

    @GetMapping
    @Operation(summary = "Get all Availabilities",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Availabilities found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No availabilities found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<AvailabilityDto>> getAllAvailabilities() throws  BusinessException {
        List<AvailabilityDto> availabilities = availabilityService.getAllAvailabilities();
        return availabilities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availabilities);
    }
}
