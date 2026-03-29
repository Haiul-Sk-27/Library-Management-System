package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.Exception.SubscriptionExpection;
import Library.Management.System.com.example.modal.Subscription;
import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.SubscriptionDTO;
import Library.Management.System.com.example.repository.SubcriptionPlanRepository;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

    private final UserRepository userRepository;
    private final SubcriptionPlanRepository planRepository;

    public SubscriptionDTO toDTO(Subscription subscription){
        if(subscription == null) return null;

        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());

        if(subscription.getUser() != null){
            dto.setUserId(subscription.getUser().getId()); // matches your DTO
            dto.setUserName(subscription.getUser().getFullName());
            dto.setUserEmail(subscription.getUser().getEmail());
        }

        if(subscription.getPlan() != null){
            dto.setPlanId(subscription.getPlan().getId());
        }

        dto.setPlanName(subscription.getPlanName());
        dto.setPlanCode(subscription.getPlanCode());
        dto.setPrice(subscription.getPrice());
        dto.setCurrency(subscription.getCurrency());
        dto.setPriceInMajorUnits(subscription.getPriceInMajorUnits());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setIsActive(subscription.getIsActive());
        dto.setMaxBooksAllowed(subscription.getMaxBooksAllowed());
        dto.setMaxDayPerBook(subscription.getMaxDaysPerBook());
        dto.setAutoRenew(subscription.getAutoRenew());
        dto.setCancelledAt(subscription.getCancelledAt());
        dto.setCancellationReason(subscription.getCancelReason());
        dto.setNotes(subscription.getNotes());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdateAt(subscription.getUpdateAt());
        dto.setDaysRemaining(subscription.getDaysRemining());
        dto.setIsValid(subscription.isValid());
        dto.setIsExpired(subscription.isExpired());

        return dto;
    }

    public Subscription toEntity(SubscriptionDTO dto) throws SubscriptionExpection {
        if(dto == null) return null;

        Subscription subscription = new Subscription();

        if(dto.getUserId() != null){
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new SubscriptionExpection("User not found id: " + dto.getUserId()));
            subscription.setUser(user);
        }

        if(dto.getPlanId() != null){
            SubscriptionPlan plan = planRepository.findById(dto.getPlanId())
                    .orElseThrow(() -> new SubscriptionExpection("Plan not found id: " + dto.getPlanId()));
            subscription.setPlan(plan);
        }

        subscription.setPlanName(dto.getPlanName());
        subscription.setPlanCode(dto.getPlanCode());
        subscription.setPrice(dto.getPrice());
        subscription.setStartDate(dto.getStartDate());
        subscription.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        subscription.setMaxDaysPerBook(dto.getMaxDayPerBook());
        subscription.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        subscription.setAutoRenew(dto.getAutoRenew());
        subscription.setCancelledAt(dto.getCancelledAt());
        subscription.setCancelReason(dto.getCancellationReason());
        subscription.setNotes(dto.getNotes());

        return subscription;
    }

    public List<SubscriptionDTO> toDTOList(List<Subscription> subscriptions){
        if(subscriptions == null) return null;
        return subscriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }
}