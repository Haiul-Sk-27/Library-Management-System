package Library.Management.System.com.example.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subcription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SubscriptionPlan plan;

    private String planName;

    private String planCode;

    private Long price;

    @Column(nullable = false)
    private Integer maxBooksAllowed;

    @Column(nullable = false)
    private Integer maxDaysPerBook;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isActive = true;

    private Boolean autoRenew;

    private LocalDateTime cancelledAt;

    private String cancelReason;

    private String notes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    public boolean isValid(){
        if(!isActive){
            return false;
        }
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);

    }

    public boolean isExpired(){
        return LocalDate.now().isAfter(endDate);
    }

    public long getDaysRemining(){
        if(isExpired()){
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(),endDate);
    }

    public  void calculateEndDate(){
        if(plan != null && startDate != null){
            this.endDate = startDate.plusDays(plan.getDurationDays())
        }
    }

    public void inirialzeFromPlan(){
        if(plan == null){
            this.planName =plan.getName();
            this.planCode = plan.getPlancode();
            this.price = plan.getPrice();
            this.maxBooksAllowed = plan.getMaxBooksAllowed();
            this.maxDaysPerBook = plan.getMaxDaysPerBook();
            if(startDate == null){
                this.startDate = LocalDate.now();
            }
        }
    }
}
