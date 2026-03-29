package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Exception.SubscriptionExpection;
import Library.Management.System.com.example.Service.SubscriptionService;
import Library.Management.System.com.example.payload.dto.SubscriptionDTO;
import Library.Management.System.com.example.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubcriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping()
    public ResponseEntity<SubscriptionDTO> subscription(
            @Valid @RequestBody SubscriptionDTO subscription
    ) throws Exception {
        SubscriptionDTO dto = subscriptionService.subscribe(subscription);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<SubscriptionDTO> getUserActiveSubcription(
            @PathVariable Long userId
    ) throws SubscriptionExpection {
        SubscriptionDTO dto =
                subscriptionService.getUserActiveSubscription(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList =
                subscriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<ApiResponse> deactivateExpiredSubscriptions() {

        subscriptionService.deactivateExpiredSubscriptions();

        ApiResponse res = new ApiResponse(
                "Expired subscriptions deactivated successfully",
                true
        );
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/cancel/{subscriptionId}/{userId}")
    public ResponseEntity<SubscriptionDTO> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam Long userId
    ) throws Throwable {
        SubscriptionDTO subscription =
                subscriptionService.cancelSubscription(subscriptionId, userId);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/activate")
    public ResponseEntity<SubscriptionDTO> activateSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId
    ) throws SubscriptionExpection {
        SubscriptionDTO subscription =
                subscriptionService.activateSubscription(subscriptionId, paymentId);
        return ResponseEntity.ok(subscription);
    }
}