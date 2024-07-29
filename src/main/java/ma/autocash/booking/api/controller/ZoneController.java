package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.service.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    @Operation(summary = "Create a new Zone",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Zone created successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> saveZone(@Valid @RequestBody ZoneDto zoneDto) throws BusinessException, TechnicalException {
        zoneService.saveZone(zoneDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zone updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Zone not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> updateZone(@PathVariable Long id, @Valid @RequestBody ZoneDto zoneDto) throws TechnicalException, BusinessException {
        zoneService.updateZone(id, zoneDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Zone deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Zone not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) throws BusinessException, TechnicalException {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zone found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Zone not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ZoneDto> getZoneById(@PathVariable Long id) throws TechnicalException, BusinessException {
        ZoneDto zone = zoneService.getZoneById(id);
        return ResponseEntity.ok(zone);
    }

    @GetMapping
    @Operation(summary = "Get all Zones",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zones found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No zones found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<ZoneDto>> getAllZones() throws TechnicalException, BusinessException {
        List<ZoneDto> zones = zoneService.getAllZones();
        return zones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(zones);
    }
}
