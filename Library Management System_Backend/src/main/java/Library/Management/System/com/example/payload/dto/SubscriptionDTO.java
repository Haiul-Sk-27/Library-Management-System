package Library.Management.System.com.example.payload.dto;

import Library.Management.System.com.example.modal.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;

    @NotNull(message = "UserId is mandatory")
    private Long userId;
    private String userName;
    private String userEmail;
    @NotNull(message = "Subcription paln id is mandatory")
    private Long planId;
    private String planName;
    private String planCode;
    private Long price;
    private String currency;
    private Double priceInMajorUnits;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Integer maxBooksAllowed;
    private Integer maxDayPerBook;
    private Boolean autoRenew;
    private LocalDateTime cancelledAt;
    private String cancellationReason;
    private String notes;
    private Long daysRemaining;
    private Boolean isValid;
    private Boolean isExpired;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}