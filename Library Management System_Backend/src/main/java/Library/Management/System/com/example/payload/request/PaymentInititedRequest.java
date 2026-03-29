package Library.Management.System.com.example.payload.request;

import Library.Management.System.com.example.domain.PaymentGateway;
import Library.Management.System.com.example.domain.PaymentStatus;
import Library.Management.System.com.example.domain.PaymentType;
import Library.Management.System.com.example.modal.Subscription;
import Library.Management.System.com.example.modal.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
}
