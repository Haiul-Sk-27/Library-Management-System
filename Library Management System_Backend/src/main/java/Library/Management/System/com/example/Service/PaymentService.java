package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.payload.dto.PaymentDTO;
import Library.Management.System.com.example.payload.request.PaymentInititedRequest;
import Library.Management.System.com.example.payload.request.PaymentVerifyRequest;
import Library.Management.System.com.example.payload.response.PaymentInitiatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiatedResponse initiatedPayment(PaymentInititedRequest req) throws Exception;
    PaymentDTO verifyPayment(PaymentVerifyRequest req);
    Page<PaymentDTO> getAllPayment(Pageable pageable);
}
