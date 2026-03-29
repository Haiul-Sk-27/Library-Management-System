package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.SubscriptionExpection;
import Library.Management.System.com.example.Service.SubscriptionService;
import Library.Management.System.com.example.Service.UserService;
import Library.Management.System.com.example.mapper.SubscriptionMapper;
import Library.Management.System.com.example.modal.Subscription;
import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.SubscriptionDTO;
import Library.Management.System.com.example.repository.SubcriptionPlanRepository;
import Library.Management.System.com.example.repository.SubscriptionRepository;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubcriptionPlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO dto) throws SubscriptionExpection {
        if (dto.getUserId() == null) {
            throw new SubscriptionExpection("UserId is mandatory");
        }
        if (dto.getPlanId() == null) {
            throw new SubscriptionExpection("PlanId is mandatory");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new SubscriptionExpection("User not found id: " + dto.getUserId()));

        SubscriptionPlan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new SubscriptionExpection("Plan not found id: " + dto.getPlanId()));

        Subscription subscription = subscriptionMapper.toEntity(dto);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.initializeFromPlan();
        subscription.calculateEndDate();
        subscription.setIsActive(true);

        Subscription saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(saved);
    }

    @Override
    public SubscriptionDTO getUserActiveSubscription(Long userId) throws SubscriptionExpection {
        LocalDate today = LocalDate.now();
        Subscription subscription = subscriptionRepository
                .findActiveSubscriptionByUserId(userId, today)
                .orElseThrow(() -> new SubscriptionExpection("No active subscription found for user id: " + userId));

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO cancelSubscription(Long subscriptionId, Long userId) throws SubscriptionExpection {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionExpection("Subscription not found id: " + subscriptionId));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new SubscriptionExpection("Subscription does not belong to user id: " + userId);
        }

        subscription.setIsActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancelReason("Cancelled by user");

        Subscription saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(saved);
    }

    @Override
    public SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionExpection {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionExpection("Subscription not found id: " + subscriptionId));

        // Add payment verification logic if required (using paymentId)
        subscription.setIsActive(true);
        subscription.setCancelledAt(null);
        subscription.setCancelReason(null);

        Subscription saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(saved);
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {
        return subscriptionRepository.findAll(pageable)
                .stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateExpiredSubscriptions() {
        LocalDate today = LocalDate.now();
        List<Subscription> expiredSubscriptions = subscriptionRepository.findExpiredActiveSubscriptions(today);

        expiredSubscriptions.forEach(sub -> sub.setIsActive(false));

        subscriptionRepository.saveAll(expiredSubscriptions);
    }
}