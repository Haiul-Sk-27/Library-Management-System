package Library.Management.System.com.example.payload.dto;

import Library.Management.System.com.example.domain.PaymentGateway;
import Library.Management.System.com.example.domain.PaymentStatus;
import Library.Management.System.com.example.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private  Long id;
    @NotNull(message = "User Id us mandatory")
    private Long userId;
    private String userName;
    private String userEmail;
    private String bookLoanId;
    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;
    private PaymentStatus status;
    @NotNull(message = "Payment gateway is mandatory")
    private PaymentGateway gateway;
    @Positive(message = "Amount must be positive")
    @NotNull(message = "Amount is mandatory")
    private Long amount;
    private String transtionId;
    private String gatewayPaymentId;
    private String gatewayOrderId;
    private String gatewaySignature;
    private String paymentMethod;
    private String description;
    private String failureReason;
    private Integer retryCount;
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
    private Boolean notifactionSent;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
