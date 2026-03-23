package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.payload.dto.SubscriptionPlanDTO;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {

    public SubscriptionPlanDTO toDRO(SubscriptionPlan plan){
        if(plan == null){
            return null;
        }

        SubscriptionPlanDTO dto =new SubscriptionPlanDTO();
        dto.setId(plan.getId());
        dto.setPlancode(plan.getPlancode());
        dto.setName(plan.getName());
        dto.setDescription(plan.getDescription());
        dto.setDurationDays(plan.getDurationDays());
        dto.setPrice(plan.getPrice());
        dto.setCurrency(plan.getCurrency());
        dto.setMaxBooksAllowed(plan.getMaxBooksAllowed());
        dto.setDisplayOrder(plan.getDisplayOrder());
        dto.setIsActive(plan.getIsActive());
        dto.setIsFeatured(plan.getIsFeatured());
        dto.setBadgeText(plan.getBadgeText());
        dto.setAdminNotes(plan.getAdminNotes());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setUpdatedAt(plan.getUpdatedAt());
        dto.setCreatedBy(plan.getCreatedBy());
        dto.setUpdatedBy(plan.getUpdatedBy());
        return  dto;
    }

    public SubscriptionPlan toEntity( SubscriptionPlanDTO dto){
        if(dto == null){
            return null;
        }

        SubscriptionPlan plan =new SubscriptionPlan();
        plan.setId(dto.getId());
        plan.setPlancode(dto.getPlancode());
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setDurationDays(dto.getDurationDays());
        plan.setPrice(dto.getPrice());
        plan.setCurrency(dto.getCurrency() != null? dto.getCurrency() :"INR");
        plan.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        plan.setDisplayOrder(dto.getDisplayOrder());
        plan.setIsActive(dto.getIsActive());
        plan.setIsFeatured(dto.getIsFeatured());
        plan.setBadgeText(dto.getBadgeText());
        plan.setAdminNotes(dto.getAdminNotes());
        plan.setCreatedAt(dto.getCreatedAt());
        plan.setUpdatedAt(dto.getUpdatedAt());
        plan.setCreatedBy(dto.getCreatedBy());
        plan.setUpdatedBy(dto.getUpdatedBy());

        return plan;
    }

    public void updateEntity(SubscriptionPlan plan,SubscriptionPlanDTO dto){
        if(plan == null || dto == null){
            return;
        }

        if(dto.getName() != null){
            plan.setName(dto.getName());
        }
        if(dto.getDescription() != null){
            plan.setDescription(dto.getDescription());
        }
        if(dto.getDescription() != null){
            plan.setDurationDays(dto.getDurationDays());
        }
        if(dto.getPrice() != null){
            plan.setPrice(dto.getPrice());
        }
        if(dto.getCurrency() != null){
            plan.setCurrency(dto.getCurrency());
        }
        if (dto.getMaxDaysPerBook() !=null){
            plan.setMaxDaysPerBook(dto.getMaxDaysPerBook());
        }
        if(dto.getDisplayOrder() !=null){
            plan.setDisplayOrder(dto.getDisplayOrder());
        }
        if(dto.getIsActive() != null){
            plan.setIsActive(dto.getIsActive());
        }
        if(dto.getIsFeatured() != null){
            plan.setIsFeatured(dto.getIsFeatured());
        }
        if(dto.getBadgeText() !=null){
            plan.setBadgeText(dto.getBadgeText());
        }
        if(dto.getAdminNotes() != null){
            plan.setAdminNotes(dto.getAdminNotes());
        }
        if(dto.getUpdatedBy() != null){
            plan.setUpdatedBy(dto.getUpdatedBy());
        }
    }
}
