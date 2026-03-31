package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.PaymentService;
import Library.Management.System.com.example.Service.gateway.RazorPayService;
import Library.Management.System.com.example.domain.PaymentGateway;
import Library.Management.System.com.example.domain.PaymentStatus;
import Library.Management.System.com.example.event.publisher.listener.PaymentEventPublisher;
import Library.Management.System.com.example.mapper.PaymentMapper;
import Library.Management.System.com.example.modal.Payment;
import Library.Management.System.com.example.modal.Subscription;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.PaymentDTO;
import Library.Management.System.com.example.payload.request.PaymentInititedRequest;
import Library.Management.System.com.example.payload.request.PaymentVerifyRequest;
import Library.Management.System.com.example.payload.response.PaymentInitiatedResponse;
import Library.Management.System.com.example.payload.response.PaymentLinkResponse;
import Library.Management.System.com.example.repository.PaymentRepossitory;
import Library.Management.System.com.example.repository.SubscriptionRepository;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepositor;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepossitory paymentRepossitory;
    private final RazorPayService razorPayService;
    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public PaymentInitiatedResponse initiatedPayment(PaymentInititedRequest req) throws Exception {
        User user = userRepositor.findById(req.getUserId()).get();

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPaymentType(req.getPaymentType());
        payment.setGateway(req.getGateway());
        payment.setAmout(req.getAmout());

        payment.setDescription(req.getDescription());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN_"+ UUID.randomUUID());
        payment.setInitiatedAt(LocalDateTime.now());


        if(req.getSubscriptionId()!= null){
            Subscription sub = subscriptionRepository
                    .findById(req.getSubscriptionId())
                    .orElseThrow(()->new Exception("Subcription not found"));

            payment.setSubscription(sub);
        }

        payment = paymentRepossitory.save(payment);

        PaymentInitiatedResponse response;

        if(req.getGateway() == PaymentGateway.RAZORPAY){
            PaymentLinkResponse paymentLinkResponse = razorPayService.createPaymentLink(
                    user,payment
            );
            response = PaymentInitiatedResponse.builder()
                    .paymentId(payment.getId())
                    .gateway(payment.getGateway())
                    .checkoutUrl(paymentLinkResponse.getPayment_link_url())
                    .transationId(paymentLinkResponse.getPayment_link_id())
                    .description(payment.getDescription())
                    .success(true)
                    .message("Payment initiated successfully")
                    .build();
            payment.getGatewayPaymentId(paymentLinkResponse.getPayment_link_id());
        }

        payment.setStatus(PaymentStatus.PENDING);
        paymentRepossitory.save(payment);
        return response;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest req) {

        boolean isValid = razorPayService.fetchPaymentDetails(
                req.getRazorpayPaymentId()
        );

        JSONObject notes = paymentDetails.getJSONobject("notes");
        Long paymentId = Long.parseLong(notes.optString("payment_id"));
        Payment payment = paymentRepossitory.findById(paymentId).get();
        boolean isValid = razorPayService.isValidPayment(req.getRazorpayPayment());
        if (PaymentGateway.RAZORPAY=== payment.getGateway()){
            if(isValid){
                payment.setStatus(req.getRazorpayPaymentId());
            }
        }

        if(isValid){
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());
            payment = paymentRepossitory.save(payment);
            paymentEventPublisher.publishPaymentSuccessEvent(payment);
        }
        return paymentMapper.toDTOPayment;
    }

    @Override
    public Page<PaymentDTO> getAllPayment(Pageable pageable) {

        Page<Payment> payments = paymentRepossitory.findAll(pageable);

        return payments.map(PaymentMapper::toDTO);
    }
}
