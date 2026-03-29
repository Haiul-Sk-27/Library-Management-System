package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.payload.dto.SubscriptionPlanDTO;
import java.util.List;

public interface SubscriptionPlanService {

    SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception;

    SubscriptionPlanDTO updateSubscriptionPlan(
            Long planId,
            SubscriptionPlanDTO subscriptionPlanDTO
    ) throws Exception;

    void deleteSubscriptionPlan(Long planId);

    List<SubscriptionPlanDTO> getAllSubcriptionPlan();
}