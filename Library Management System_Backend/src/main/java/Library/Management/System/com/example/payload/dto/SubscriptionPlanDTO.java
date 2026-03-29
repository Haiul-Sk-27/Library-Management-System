package Library.Management.System.com.example.payload.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {

    private Long id;

    @NotNull
    private String plancode;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Positive
    private Integer durationDays;

    @NotNull
    @Positive
    private Long price;

    private String currency;

    @NotNull
    @Positive
    private Integer maxBooksAllowed;

    @NotNull
    @Positive
    private Integer maxDaysPerBook;

    private Integer displayOrder;

    private Boolean isActive;
    private Boolean isFeatured;

    private String badgeText;
    private String adminNotes;

    private String createdBy;
    private String updatedBy;
}
