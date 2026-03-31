package Library.Management.System.com.example.repository;

import Library.Management.System.com.example.modal.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubcriptionPlanRepository extends JpaRepository<SubscriptionPlan,Long> {
    boolean existsByPlancode(String plancode);

    // Find plan by plan code
    Optional<SubscriptionPlan> findByPlancode(String plancode);

    SubscriptionPlan findByCode(String planCode);
}
