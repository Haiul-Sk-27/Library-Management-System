package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Service.SubscriptionPlanService;
import Library.Management.System.com.example.payload.dto.SubscriptionPlanDTO;
import Library.Management.System.com.example.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/subscription-plans")
@RestController
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    // GET all subscription plans
    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans() {
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubcriptionPlan();
        return ResponseEntity.ok(plans);
    }

    // CREATE a subscription plan
    @PostMapping("/admin/create")
    public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(
            @Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO
    ) throws Exception {
        SubscriptionPlanDTO plan = subscriptionPlanService.createSubscriptionPlan(subscriptionPlanDTO);
        return ResponseEntity.ok(plan);
    }

    // UPDATE a subscription plan
    @PutMapping("/admin/{id}")
    public ResponseEntity<SubscriptionPlanDTO> updateSubscriptionPlan(
            @Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO,
            @PathVariable Long id
    ) throws Exception {
        SubscriptionPlanDTO plan = subscriptionPlanService.updateSubscriptionPlan(id, subscriptionPlanDTO);
        return ResponseEntity.ok(plan);
    }

    // DELETE a subscription plan
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse> deleteSubscriptionPlan(@PathVariable Long id) {
        subscriptionPlanService.deleteSubscriptionPlan(id);
        ApiResponse res = new ApiResponse("Plan deleted successfully", true);
        return ResponseEntity.ok(res);
    }
}
