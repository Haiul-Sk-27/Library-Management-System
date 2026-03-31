package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.SubscriptionPlanService;
import Library.Management.System.com.example.Service.UserService;
import Library.Management.System.com.example.mapper.SubscriptionPlanMapper;
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
    private final SubscriptionPlanMapper planMapper;
    private final UserService userService;

    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {
        if(planRepository.existsByPlancode(planDTO.getPlancode())){
            throw new Exception("Plan code already exists");
        }

        SubscriptionPlan plan = planMapper.toEntity(planDTO);
        User currentUser = userService.getCurrentUser();
        plan.setCreatedBy(currentUser.getFullName());
        plan.setUpdatedBy(currentUser.getFullName());

        SubscriptionPlan savedPlan = planRepository.save(plan);
        return planMapper.toDTO(savedPlan);
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception {
        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                () -> new Exception("Plan not found")
        );

        planMapper.updateEntity(existingPlan, planDTO);

        User currentUser = userService.getCurrentUser();
        existingPlan.setUpdatedBy(currentUser.getFullName());

        SubscriptionPlan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toDTO(updatedPlan);
    }

    @Override
    public void deleteSubscriptionPlan(Long planId) {
        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                () -> new RuntimeException("Plan not found")
        );

        planRepository.delete(existingPlan);
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubcriptionPlan() {
        List<SubscriptionPlan> planList = planRepository.findAll();
        return planList.stream()
                .map(planMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionPlan getBySuncriptionPlanCode(String subscriptionPlanCode) throws Exception {
        SubscriptionPlan plan = planRepository.findByCode(subscriptionPlanCode);
        if(plan == null){
            throw  new Exception("Plan not found");
        }

        return  plan;
    }
}
