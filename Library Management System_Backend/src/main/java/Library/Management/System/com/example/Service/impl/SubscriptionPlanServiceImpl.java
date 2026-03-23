package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.SubscriptionPlanService;
import Library.Management.System.com.example.Service.UserService;
import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.SubscriptionPlanDTO;
import Library.Management.System.com.example.repository.SubcriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubcriptionPlanRepository planRepository;
    private final SubscriptionPlan planMapper;
    private final UserService userService;
    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {
        if(planRepository.existsByPlanCode(planDTO.getPlancode())){
            throw  new Exception("Plan code is alredy exists");
        }

        SubscriptionPlan plan =planMapper.toEntity(planDTO);
        User currentUser = userService.getCurrentUser();
        plan.setCreatedBy(currentUser.getFullName());
        plan.setUpdatedBy(currentUser.getFullName());
        SubscriptionPlan savedPlan = planRepository.save(plan);
        return planMapper.toDTO(savedPlan);
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId) throws Exception {

        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                ()->new Exception("Plan not found")
        );
        planMapper.updateEntity(existingPlan,planDTO);
        User currentUser = userService.getCurrentUser();
        existingPlan.setUpdatedBy(currentUser.getFullName());
        SubscriptionPlan updatePlan = planRepository.save(existingPlan);
        return planMapper.toDTO(updatePlan);
    }

    @Override
    public void deleteSubccriptionPlan(Long palaId) {
        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                ()->new Exception("Plan not found")
        );
        planRepository.delete(existingPlan);
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubcriptionPlan() {
        List<SubscriptionPlan> planList = planRepository.findAll();
        return planList.stream().map(
               planMapper::toDTO
        ).collect(Collectors.toList());
    }
}
