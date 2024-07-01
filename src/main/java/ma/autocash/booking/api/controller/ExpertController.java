package ma.autocash.booking.api.controller;

import ma.autocash.booking.api.dto.ExpertDto;
import ma.autocash.booking.api.services.ExpertService;
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
    public ExpertDto saveExpert(@RequestBody ExpertDto expertDto) {
        return expertService.saveExpert(expertDto);
    }

    @PutMapping("/{id}")
    public ExpertDto updateExpert(@PathVariable Long id, @RequestBody ExpertDto expertDto) {
        return expertService.updateExpert(id, expertDto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpert(@PathVariable Long id) {
        expertService.deleteExpert(id);
    }

    @GetMapping
    public List<ExpertDto> getAllExperts() {
        return expertService.getAllExperts();
    }

    @GetMapping("/{id}")
    public ExpertDto getExpertById(@PathVariable Long id) {
        return expertService.getExpertById(id);
    }

    @PostMapping("/{expertId}/assign-zone/{zoneId}")
    public ExpertDto assignZoneToExpert(@PathVariable Long expertId, @PathVariable Long zoneId) {
        return expertService.assignZoneToExpert(expertId, zoneId);
    }
}
