package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.exception.BusinessException;
import ma.autocash.booking.api.exception.TechnicalException;
import ma.autocash.booking.api.service.ExpertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
                    @ApiResponse(responseCode = "201", description = "Expert created successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ExpertDto> saveExpert(@Valid @RequestBody ExpertDto expertDto) throws TechnicalException, BusinessException {
        ExpertDto savedExpert = expertService.saveExpert(expertDto);
        return new ResponseEntity<>(savedExpert, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expert updated successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ExpertDto> updateExpert(@PathVariable Long id, @Valid @RequestBody ExpertDto expertDto) throws TechnicalException, BusinessException {
        ExpertDto updatedExpert = expertService.updateExpert(id, expertDto);
        return ResponseEntity.ok(updatedExpert);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Expert deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Expert not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deleteExpert(@PathVariable Long id) throws TechnicalException, BusinessException {
        expertService.deleteExpert(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expert found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ExpertDto> getExpertById(@PathVariable Long id) throws TechnicalException, BusinessException {
        ExpertDto expert = expertService.getExpertById(id);
        return expert != null ? ResponseEntity.ok(expert) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all Experts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Experts found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "No experts found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<List<ExpertDto>> getAllExperts() throws TechnicalException, BusinessException {
        List<ExpertDto> experts = expertService.getAllExperts();
        return experts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(experts);
    }

    @PutMapping("/{expertId}/assign-zones")
    @Operation(summary = "Assign a list of Zones to an Expert by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zones assigned to Expert successfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Expert not found or other technical issues"),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<ExpertDto> assignZonesToExpert(@PathVariable Long expertId, @RequestBody List<Long> zoneIds) throws TechnicalException, BusinessException {
        ExpertDto updatedExpert = expertService.assignZonesToExpert(expertId, zoneIds);
        return ResponseEntity.ok(updatedExpert);
    }
}
