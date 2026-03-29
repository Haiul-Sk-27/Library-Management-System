package Library.Management.System.com.example.modal;

import Library.Management.System.com.example.domain.PaymentGateway;
import Library.Management.System.com.example.domain.PaymentStatus;
import Library.Management.System.com.example.domain.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Email
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subscription subscription;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    private Long amout;

    private String transactionId;
    private String gatewayPaymentId;
    private String getwayOrderId;
    private String gatewaySignature;
    private String description;
    private String failureReason;
    @CreationTimestamp
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
