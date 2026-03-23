package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.SubcriptionExpection;
import Library.Management.System.com.example.Service.SubcriptionService;
import Library.Management.System.com.example.Service.UserService;
import Library.Management.System.com.example.mapper.SubcriptionMapper;
import Library.Management.System.com.example.modal.Subcription;
import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.SubcriptionDTO;
import Library.Management.System.com.example.repository.SubcriptionPlanRepository;
import Library.Management.System.com.example.repository.SubcriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcriptionImpl implements SubcriptionService {

    private final SubcriptionRepository subcriptionRepository;
    private final SubcriptionMapper subcriptionMapper;
    private final UserService userService;
    private final SubcriptionPlanRepository subcriptionPlanRepository;

    @Override
    public SubcriptionDTO subcribe(SubcriptionDTO subcriptionDTO) {

        User user = userService.getCurrentUser();
        SubscriptionPlan plan = subcriptionPlanRepository
                .findById(subscriptionDTO.getPlanId().orElseThrow
                        ()-> new SubcriptionExpection("Plan not found");
                );

        //Optional<>
        Subscription subscription = subcriptionMapper.toEntity(subcriptionDTO);
        subscription.initializeFromPlan();
        Subcription.setIsActive(false);
        Subcription savedSabcription =subcriptionRepository.save(subscription);
        //createPayment(todo)
        return subcriptionMapper.toDTO(savedSubcription);
    }

    @Override
    public SubcriptionDTO getUserActiveSubcription(Long userId) throws Exception {

        User user = userService.getCurrentUser();
        Subscription subscription = subcriptionRepository.findActiveSubscriptionByUserId(user.getId(), LocalDate.now());

        return subcriptionMapper.toDTO(subscription);
    }

    @Override
    public SubcriptionDTO cancelSubscription(Long subscriptionId, Long userId) throws Throwable {
        Subcription subcription = subcriptionRepository.findById(subscriptionId)
                .orElseThrow(()->new SubcriptionExpection("Subscription not found with id:"+subscriptionId));

        if(!subcription.getIsActive()){
            throw new SubcriptionExpection("Subcription is already inactive");
        }

        subcription.setIsActive(false);
        subcription.setCancelledAt(LocalDateTime.now());
        subcription.setCancelReason(reason != null ? reason : "Cancelled by user");
        subcription=subcriptionRepository.save(subcription);
        log.info("Subscription cancelled successfully:{}",subscriptionId);
        return subcriptionMapper.toDTO(subcription);
    }

    @Override
    public SubcriptionDTO activeSubcription(Long subscriptionId, Long paymentId) {

        Subcription subcription = subcriptionRepository.findById(subscriptionId)
                .orElseThrow(()->new SubcriptionExpection("Subcription not found by id!"));
        //verify payment(todo)
        subcription.setIsActive(true);
        subcription.setStartDate(LocalDate.now());
        subcription.calculateEndDate();
        subcription = subcriptionRepository.save(subcription);
        return subcriptionMapper.toDTO(subcription);
    }

    @Override
    public List<SubcriptionDTO> getAllSubscriptions(Pageable pageable) {
        List<Subcription> subcriptions = subcriptionRepository.findAll();
        return SubcriptionMapper.toDTOList(subcriptions);
    }

    @Override
    public void deactivateExpiredSubscriptions() {
        List<Subcription> expiredSubcriptions = subcriptionRepository.
                findExpiredActiveSubcriptions(LocalDate.now());

        for(Subcription subcription : expiredSubcriptions){
            subcription.setIsActive(false);
            subcriptionRepository.save(subcription);
        }
    }
}
