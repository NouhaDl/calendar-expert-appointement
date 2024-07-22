package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ZoneDto;
import ma.autocash.booking.api.service.ZoneService;
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
                    @ApiResponse(responseCode = "200", description = "Zone created successfully",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ZoneDto> saveZone(@Valid @RequestBody ZoneDto zoneDto) {
        try {
            ZoneDto savedZone = zoneService.saveZone(zoneDto);
            return ResponseEntity.ok(savedZone);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zone updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Zone not found")
            })
    public ResponseEntity<ZoneDto> updateZone(@PathVariable Long id, @Valid @RequestBody ZoneDto zoneDto) {
        try {
            ZoneDto updatedZone = zoneService.updateZone(id, zoneDto);
            return ResponseEntity.ok(updatedZone);
        } catch (Exception e) {

            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Zone deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Zone not found")
            })
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        try {
            zoneService.deleteZone(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {

            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Zone by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zone found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Zone not found")
            })
    public ResponseEntity<ZoneDto> getZoneById(@PathVariable Long id) {
        try {
            ZoneDto zone = zoneService.getZoneById(id);
            return zone != null ?
                    ResponseEntity.ok(zone) :
                    ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all Zones",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zones found",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<ZoneDto>> getAllZones() {
        try {
            List<ZoneDto> zones = zoneService.getAllZones();
            return ResponseEntity.ok(zones);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
