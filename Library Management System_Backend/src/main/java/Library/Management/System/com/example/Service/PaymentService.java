package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.payload.request.PaymentInititedRequest;
import Library.Management.System.com.example.payload.response.PaymentInitiatedResponse;

public interface PaymentService {

    PaymentInitiatedResponse initiatedPayment(PaymentInititedRequest req);
}
