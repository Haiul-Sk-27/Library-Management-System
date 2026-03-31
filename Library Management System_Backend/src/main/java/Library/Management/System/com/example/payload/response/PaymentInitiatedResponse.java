package Library.Management.System.com.example.payload.response;

import Library.Management.System.com.example.domain.PaymentGateway;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class PaymentInitiatedResponse {

    private Long paymentId;
    private PaymentGateway gateway;
    private String razorpayOrderId;
    protected Long amount;
    private String description;
    private String checkOutUrl;
    private String message;
    private String success;
}
