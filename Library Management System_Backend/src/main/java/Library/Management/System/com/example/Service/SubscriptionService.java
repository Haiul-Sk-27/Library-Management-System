package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.Exception.SubscriptionExpection;
import Library.Management.System.com.example.payload.dto.SubscriptionDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDTO subscribe(SubscriptionDTO dto) throws Exception;

    SubscriptionDTO getUserActiveSubscription(Long userId) throws SubscriptionExpection;

    SubscriptionDTO cancelSubscription(Long subscriptionId, Long userId) throws Throwable;

    SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionExpection;

    List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);

    void deactivateExpiredSubscriptions();

}