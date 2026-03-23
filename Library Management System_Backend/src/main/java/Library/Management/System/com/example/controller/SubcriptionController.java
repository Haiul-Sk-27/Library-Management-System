package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Service.SubcriptionService;
import Library.Management.System.com.example.payload.dto.SubcriptionDTO;
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

    private final SubcriptionService subcriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscription(
            @Valid @ResponseBody SubcriptionDTO subscription
            ){
        SubcriptionDTO dto = subcriptionService.subcribe(subscription);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUsersActiveSubcriptions(
            @RequestParam(required = false) Long userId
    ){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        List<SubcriptionDTO> dtoList = SubcriptionService.getUserActiveSubcription(pageable);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscriptions(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        List<SubcriptionDTO> dtoList = subcriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/deactive-expired")
    public ResponseEntity<?> deactiveExpiredSubscriptions(){
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page,size);
        subcriptionService.deactivateExpiredSubscriptions();
        ApiResponse res = new ApiResponse("Task is done!",true);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestMapping(required = false) String reason
    ){
        SubcriptionDTO subscription = subcriptionService
                .cancelSubscription(subscriptionId,reason);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/active")
    public ResponseEntity<?> activeSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId
    ){
        SubcriptionDTO subscription = subcriptionService.activeSubcription(subscriptionId,paymentId);
        return ResponseEntity.ok(subscription);
    }
}
