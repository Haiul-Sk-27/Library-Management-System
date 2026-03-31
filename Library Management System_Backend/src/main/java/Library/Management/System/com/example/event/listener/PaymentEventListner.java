package Library.Management.System.com.example.event.listener;

import Library.Management.System.com.example.Service.SubscriptionService;
import Library.Management.System.com.example.modal.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListner {

    private final SubscriptionService subscriptionService;
    @Async
    @EventListener
    @Transactional
    public void handlePaymentSuccess(Payment payment){
        switch (payment.getPaymentType()){
            case FINE:
            case LOST_BOOK_PENALTY:
            case DAMAGED_BOOK_PENALTY:
                break;

            case MEMBERSHIP:
                subscriptionService.activateSubscription(payment.getSubscription().getId(),payment.getId());


        }
    }
}
