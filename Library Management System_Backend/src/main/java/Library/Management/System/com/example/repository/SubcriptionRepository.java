package Library.Management.System.com.example.repository;

import Library.Management.System.com.example.modal.Subcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubcriptionRepository extends JpaRepository {

    @Query("select s from Subscription s Where s.userId = :userId AND"+
    "s.isActive = true AND"+
    "s.startDate <=:today and s.endDate >= :today"
    )
    Optional<Subcription> findActiveSubscriptionByUserId(
            @Param("userId") Long userId,
            @Param("today") LocalDate today
    );

    @Query("select s from Subcription s where s.isActive=true"+
    "AND s.endDate<:today")
    List<Subcription> findExpiredActiveSubcriptions(
            @Param("today") LocalDate today
    );
}
