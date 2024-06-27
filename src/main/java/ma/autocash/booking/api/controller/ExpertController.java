package ma.autocash.booking.api.controller;
import ma.autocash.booking.api.entity.Expert;
import ma.autocash.booking.api.provider.ExpertProvider;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/experts")
public class ExpertController {

    private final ExpertProvider expertProvider;

    public ExpertController(ExpertProvider expertProvider) {
        this.expertProvider = expertProvider;
    }

    @PostMapping
    public Expert saveExpert(@RequestBody Expert expert) {
        return expertProvider.saveExpert(expert);
    }

    @PutMapping("/{id}")
    public Expert updateExpert(@PathVariable Long id, @RequestBody Expert expert) {
        expert.setId(id);
        return expertProvider.updateExpert(expert);
    }

    @DeleteMapping("/{id}")
    public void deleteExpert(@PathVariable Long id) {
        expertProvider.deleteExpert(id);
    }
    @GetMapping
    public List<Expert> getAllExperts() {
        return expertProvider.getAllExperts();
    }
    @GetMapping("/{id}")
    public Expert getExpertById(@PathVariable Long id) {
        return expertProvider.getExpertById(id);
    }

    @PostMapping("/{expertId}/assign-zone/{zoneId}")
    public Expert assignZoneToExpert(@PathVariable Long expertId, @PathVariable Long zoneId) {
        return expertProvider.assignZoneToExpert(expertId, zoneId);
    }


}
