package Library.Management.System.com.example.payload.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {

    private Long id;

    @Column(nullable = false,unique = true)
    private String plancode;

    @Column(nullable = false,length = 100)
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Duration must be poritive")
    private Integer durationDays;

    @Column(nullable = false)
    private Long price;

    private String currency = "INR";

    @NotNull(message = "Max books per book is mandatory")
    @Positive(message = "Max book must be positive")
    private Integer maxBooksAllowed;

    @NotNull(message = "Max book allowed is mandatory")
    @Positive(message = "Max Days must be positive")
    private Integer maxDaysPerBook;

    private Integer displayOrder=0;

    private Boolean isActive = true;
    private Boolean isFeatured = true;

    private String badgeText;

    private String adminNotes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
}
