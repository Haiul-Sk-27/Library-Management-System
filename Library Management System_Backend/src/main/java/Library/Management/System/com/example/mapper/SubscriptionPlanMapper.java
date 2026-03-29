package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.payload.dto.SubscriptionPlanDTO;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {

    public SubscriptionPlanDTO toDTO(SubscriptionPlan plan) {
        if (plan == null) return null;

        return SubscriptionPlanDTO.builder()
                .id(plan.getId())
                .plancode(plan.getPlancode())
                .name(plan.getName())
                .description(plan.getDescription())
                .durationDays(plan.getDurationDays())
                .price(plan.getPrice())
                .currency(plan.getCurrency())
                .maxBooksAllowed(plan.getMaxBooksAllowed())
                .maxDaysPerBook(plan.getMaxDaysPerBook())
                .displayOrder(plan.getDisplayOrder())
                .isActive(plan.getIsActive())
                .isFeatured(plan.getIsFeatured())
                .badgeText(plan.getBadgeText())
                .adminNotes(plan.getAdminNotes())
                .createdBy(plan.getCreatedBy())
                .updatedBy(plan.getUpdatedBy())
                .build();
    }

    public SubscriptionPlan toEntity(SubscriptionPlanDTO dto) {
        if (dto == null) return null;

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setPlancode(dto.getPlancode());
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setDurationDays(dto.getDurationDays());
        plan.setPrice(dto.getPrice());
        plan.setCurrency(dto.getCurrency());
        plan.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        plan.setMaxDaysPerBook(dto.getMaxDaysPerBook());
        plan.setDisplayOrder(dto.getDisplayOrder());
        plan.setIsActive(dto.getIsActive());
        plan.setIsFeatured(dto.getIsFeatured());
        plan.setBadgeText(dto.getBadgeText());
        plan.setAdminNotes(dto.getAdminNotes());
        plan.setCreatedBy(dto.getCreatedBy());
        plan.setUpdatedBy(dto.getUpdatedBy());
        return plan;
    }

    public void updateEntity(SubscriptionPlan plan, SubscriptionPlanDTO dto) {
        if (plan == null || dto == null) return;

        if (dto.getName() != null) plan.setName(dto.getName());
        if (dto.getDescription() != null) plan.setDescription(dto.getDescription());
        if (dto.getDurationDays() != null) plan.setDurationDays(dto.getDurationDays());
        if (dto.getPrice() != null) plan.setPrice(dto.getPrice());
        if (dto.getCurrency() != null) plan.setCurrency(dto.getCurrency());
        if (dto.getMaxBooksAllowed() != null) plan.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        if (dto.getMaxDaysPerBook() != null) plan.setMaxDaysPerBook(dto.getMaxDaysPerBook());
        if (dto.getDisplayOrder() != null) plan.setDisplayOrder(dto.getDisplayOrder());
        if (dto.getIsActive() != null) plan.setIsActive(dto.getIsActive());
        if (dto.getIsFeatured() != null) plan.setIsFeatured(dto.getIsFeatured());
        if (dto.getBadgeText() != null) plan.setBadgeText(dto.getBadgeText());
        if (dto.getAdminNotes() != null) plan.setAdminNotes(dto.getAdminNotes());
        if (dto.getUpdatedBy() != null) plan.setUpdatedBy(dto.getUpdatedBy());
    }
}
