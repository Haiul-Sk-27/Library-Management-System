package Library.Management.System.com.example.payload.request;

import Library.Management.System.com.example.domain.PaymentGateway;
import Library.Management.System.com.example.domain.PaymentStatus;
import Library.Management.System.com.example.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInititedRequest {

    @NotNull(message = "User id is mandatory")
    private Long userId;
    private Long bookLoanId;

    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    @NotNull(message = "Payment gateway is mandatory")
    private PaymentGateway gateway;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Long amout;

    @Size(min = 3,max = 3, message = "Currency must be 3-letter code (e.g., INR,USD)")
    private String currency = "INR";

    @Size(max = 500,message = "Description must not exceed 500 characters")
    private String description;

    private Long fineId;

    private Long subscriptionId;
    @Size(max = 500,message = "Success URL must not exceed 500 characters")
    private String successUrl;
    @Size(max = 500,message = "Cancel url msut not exceed 500 characters")
    private String cancelUrl;
}
