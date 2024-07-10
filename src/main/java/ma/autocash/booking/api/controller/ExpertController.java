package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.services.ExpertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/experts")
public class ExpertController {

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    @PostMapping
    @Operation(summary = "Create a new Expert",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expert created successfully",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ExpertDto> saveExpert(@RequestBody ExpertDto expertDto) {
        ExpertDto savedExpert = expertService.saveExpert(expertDto);
        return ResponseEntity.ok(savedExpert);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expert updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert not found")
            })
    public ResponseEntity<ExpertDto> updateExpert(@PathVariable Long id, @RequestBody ExpertDto expertDto) {
        try {
            ExpertDto updatedExpert = expertService.updateExpert(id, expertDto);
            return ResponseEntity.ok(updatedExpert);
        } catch (TechnicalException e) {
            // Handle the exception appropriately, e.g., return a 500 status code
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Expert deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Expert not found")
            })
    public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
        expertService.deleteExpert(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expert found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert not found")
            })
    public ResponseEntity<ExpertDto> getExpertById(@PathVariable Long id) {
        ExpertDto expert = expertService.getExpertById(id);
        return expert != null ?
                ResponseEntity.ok(expert) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all Experts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Experts found",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<ExpertDto>> getAllExperts() {
        List<ExpertDto> experts = expertService.getAllExperts();
        return ResponseEntity.ok(experts);
    }

    @PutMapping("/{expertId}/assign-zone/{zoneId}")
    @Operation(summary = "Assign a Zone to an Expert by IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zone assigned to Expert successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert or Zone not found")
            })
    public ResponseEntity<ExpertDto> assignZoneToExpert(@PathVariable Long expertId, @PathVariable Long zoneId) {
        ExpertDto updatedExpert = expertService.assignZoneToExpert(expertId, zoneId);
        return ResponseEntity.ok(updatedExpert);
    }
}
