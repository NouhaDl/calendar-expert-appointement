package com.autocash.autocashapi.Controllers;
import com.autocash.autocashapi.Dtos.ExpertDto;
import com.autocash.autocashapi.Services.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experts")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    @PostMapping
    public ResponseEntity<ExpertDto> createExpert(@RequestBody ExpertDto expertDto) {
        ExpertDto createdExpert = expertService.saveExpert(expertDto);
        return new ResponseEntity<>(createdExpert, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpertDto>> getAllExperts() {
        List<ExpertDto> experts = expertService.findAllExperts();
        return new ResponseEntity<>(experts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertDto> getExpertById(@PathVariable Long id) {
        ExpertDto expertDto = expertService.findExpertById(id);
        return new ResponseEntity<>(expertDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpertDto> updateExpert(@PathVariable Long id, @RequestBody ExpertDto expertDto) {
        ExpertDto updatedExpert = expertService.updateExpert(id, expertDto);
        return new ResponseEntity<>(updatedExpert, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
        boolean deleted = expertService.deleteExpert(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{expertId}/assign-zone/{zoneId}")
    public ResponseEntity<ExpertDto> assignExpertToZone(@PathVariable Long expertId, @PathVariable Long zoneId) {
        ExpertDto updatedExpert = expertService.assignExpertToZone(expertId, zoneId);
        return new ResponseEntity<>(updatedExpert, HttpStatus.OK);
    }

    // Add other controller methods as needed
}
