package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.Exception.SubcriptionExpection;
import Library.Management.System.com.example.modal.Subcription;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.SubcriptionDTO;
import Library.Management.System.com.example.repository.SubcriptionPlanRepository;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SubcriptionMapper {

    private final UserRepository userRepository;
    private final SubcriptionPlanRepository planRepository;

    public SubcriptionDTO toDTO(Subcription subcription){
        if(subcription == null){
            return  null;
        }

        SubcriptionDTO dto = new SubcriptionDTO();
        dto.setId(subcription.getId());


        if(subcription.getUser() != null){
            dto.setUserId(subcription.getUser().getId());
            dto.setUserName(subcription.getUser().getFullName());
            dto.setUserEmail(subcription.getUser().getEmail());
        }

        if(subcription.getPlan() != null){
            dto.setPlanId(subcription.getPlan().getId());
        }

        dto.setPlanName(subcription.getPlanName());
        dto.setPlanCode(subcription.getPlanCode());
        dto.setPrice(subcription.getPrice());
        dto.setCurrency(subcription.getCurrnecy());
        dto.setPriceInMajorUnits(subcription.getPriceInMajorUnits());
        dto.setStartDate(subcription.getStartDate());
        dto.setEndDate(subcription.getEndDate());
        dto.setIsActive(subcription.getIsActive());
        dto.setMaxBooksAllowed(subcription.getMaxBooksAllowed());
        dto.setAutoRenew(subcription.getAutoRenew());
        dto.setCancelledAt(subcription.getCancelledAt());
        dto.setCancellationReason(subcription.getCancelReason());
        dto.setNotes(subcription.getNotes());
        dto.setCreatedAt(subcription.getCreatedAt());
        dto.setCreatedAt(subcription.getCreatedAt());
        dto.setUpdateAt(subcription.getUpdateAt());

        //calculated fields
        dto.setDaysRemaining(subcription.getDaysRemaining());
        dto.setIsValid(subcription.isValid());
        dto.setIsExpired(subcription.isExpired());

        return dto;
    }

    public Subcription toEntity(SubcriptionDTO dto) throws SubcriptionExpection{
        if(dto == null){
            return  null;
        }

        //User Map
        if(dto.getUserId() != null){
            User user = userRepository.findById(dto.getId())
                    .orElseThrow(()->new SecurityException("User not found id: "+dto.getId()));
                    Subcription.setUser(user);
        }

        //Map Plan
        if(dto.getPlanId() != null){
            Subcription plan = planRepository.findById(dto.getPlanId())
                    .orElseThrow(()->new SubcriptionExpection())
        }

        Subcription.setPlanName(dto.getPlanName());
        Subcription.setPlanCode(dto.getPlanCode());
        Subcription.setPrice(dto.getPrice());
        Subcription.setStartDate(dto.getStartDate());
        Subcription.setIsActive(dto.getIsActive() != null ? dto.getIsActive():true);
        Subcription.setMaxDaysPerBook(dto.getMaxDayPerBook());
        Subcription.setMaxBookAllowed(dto.getMaxBooksAllowed());
        Subcription.setAutoRenew(dto.getAutoRenew());
        Subcription.setCancelledAt(dto.getCancelledAt());
        Subcription.setCancellationReason(dto.getCancellationReason());
        Subcription.setNotes(dto.getNotes());

        return Subcription;
    }

    public List<Subcription> toDTOList(List<Subcription> subcriptions){
        if(subcriptions == null){
            return  null;
        }

        return subcriptions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
