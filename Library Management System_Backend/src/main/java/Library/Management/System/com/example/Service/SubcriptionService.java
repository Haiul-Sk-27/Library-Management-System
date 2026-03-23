package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.modal.Subcription;
import Library.Management.System.com.example.payload.dto.SubcriptionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubcriptionService {

    SubcriptionDTO subcribe(SubcriptionDTO subcriptionDTO);
    SubcriptionDTO getUserActiveSubcription(Long userId) throws Exception;
    SubcriptionDTO cancelSubscription(Long subscriptionId,Long userId) throws Throwable;
    SubcriptionDTO activeSubcription(Long subscriptionId,Long paymentId);
    List<SubcriptionDTO> getAllSubscriptions(Pageable pageable);
    void deactivateExpiredSubscriptions();
}
