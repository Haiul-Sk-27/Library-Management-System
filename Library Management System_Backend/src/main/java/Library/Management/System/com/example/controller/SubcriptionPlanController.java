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
public class SubcriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @GetMapping
    public ResponseEntity<?> getAllSuncriptionPlans(){
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubcriptionPlan();
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> getAllSubcriptionPlans(
            @Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO
            ) throws Exception {
        SubscriptionPlanDTO plans = subscriptionPlanService.createSubscriptionPlan(subscriptionPlanDTO)
        return ResponseEntity.ok(plans);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateSubcriptionPlan(
            @Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO,
            @PathVariable long id
    ){
        SubscriptionPlanDTO plans = subscriptionPlanService.updateSubscriptionPlan(id,subscriptionPlanDTO);
        return ResponseEntity.ok(plans);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteSuncriptionPlan(
            @PathVariable long id
    ){
        SubscriptionPlanService.deleteSubccriptionPlan(id);
        ApiResponse res = new ApiResponse("Plan deleted SuccessFully",true);
        return ResponseEntity.ok(res);
    }
}
