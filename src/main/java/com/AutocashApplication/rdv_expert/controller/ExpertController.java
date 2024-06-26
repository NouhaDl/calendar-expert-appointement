package com.AutocashApplication.rdv_expert.controller;
import com.AutocashApplication.rdv_expert.entity.Expert;
import com.AutocashApplication.rdv_expert.service.ExpertService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/experts")
public class ExpertController {

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    @PostMapping
    public Expert saveExpert(@RequestBody Expert expert) {
        return expertService.saveExpert(expert);
    }

    @PutMapping("/{id}")
    public Expert updateExpert(@PathVariable Long id, @RequestBody Expert expert) {
        expert.setId(id);
        return expertService.updateExpert(expert);
    }

    @DeleteMapping("/{id}")
    public void deleteExpert(@PathVariable Long id) {
        expertService.deleteExpert(id);
    }
    @GetMapping
    public List<Expert> getAllExperts() {
        return expertService.getAllExperts();
    }
    @GetMapping("/{id}")
    public Expert getExpertById(@PathVariable Long id) {
        return expertService.getExpertById(id);
    }

    @PostMapping("/{expertId}/assign-zone/{zoneId}")
    public Expert assignZoneToExpert(@PathVariable Long expertId, @PathVariable Long zoneId) {
        return expertService.assignZoneToExpert(expertId, zoneId);
    }


}
