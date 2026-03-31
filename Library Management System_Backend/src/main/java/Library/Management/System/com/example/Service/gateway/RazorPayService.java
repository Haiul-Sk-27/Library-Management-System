package Library.Management.System.com.example.Service.gateway;

import Library.Management.System.com.example.Service.SubscriptionPlanService;
import Library.Management.System.com.example.domain.PaymentType;
import Library.Management.System.com.example.modal.Payment;
import Library.Management.System.com.example.modal.SubscriptionPlan;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorPayService {

    private final SubscriptionPlanService subscriptionPlanService;

    @Value("${razorpay.key.id:}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Value("${razorpay.callback.base-url:http://localhost:5173}")
    private String callBackBaseUrl;

    public PaymentLinkResponse createPaymentLink(User user, Payment payment) throws Exception {

        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
            Long amountInPaisa = payment.getAmout()*(new java.math.BigDecimal("100")).intValue();
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amountInPaisa);
            paymentLinkRequest.put("currency","INR");
            paymentLinkRequest.put("description",payment.getDescription());

            JSONObject customer = new JSONObject();
            customer.put("Name",user.getFullName());
            customer.put("email",user.getEmail());
            if(user.getPhone() != null){
                customer.put("contact",user.getPhone());
            }

            paymentLinkRequest.put("customer",user.getPhone());
            JSONObject notify = new JSONObject();
            notify.put("email",true);
            notify.put("sms",user.getPhone() != null);

            //Enable reminder
            paymentLinkRequest.put("Reminder_enable",true);

            //callback configaration
            String successUrl = callBackBaseUrl+"/payment-success/"+payment.getId();

            JSONObject notes = new JSONObject();
            paymentLinkRequest.put("callback_url",successUrl);
            paymentLinkRequest.put("cakkback_method","get");

            if(payment.getPaymentType() == PaymentType.MEMBERSHIP){
                notes.put("Subcription_id",payment.getSubscription().getId());
                notes.put("plan",payment.getSubscription().getPlan().getId());
                notes.put("type",PaymentType.MEMBERSHIP);
            }else if(payment.getPaymentType() == PaymentType.FINE){
//                todo
//                notes.put("fine_id",payment.getFine().getId());
                notes.put("type",PaymentType.FINE);
            }

            paymentLinkRequest.put("note",notes);
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            String paymentUrl = paymentLink.get("short_url");
            String paymentLinkedId = paymentLink.get("id");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentUrl);
            response.setPayment_link_id(paymentLinkedId);
            return response;
        }catch (RazorpayException e){
            throw  new RuntimeException(e);
        }

        public JSONObject fetchPaymentDetails(String paymentId) throw Exception{
            validateConfigaration();

            try{
                RazorpayClient razorpay = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
                com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);
                return payment.toJson();

            }catch (RazorpayException e){
                throw new Exception("Failed to fetch payment details: "+e.getMessage(),e);
            }
        }

        public boolean isValidPayment(String paymentId) {
            try{
                JSONObject paymentDetails = fetchPaymentDetails(paymentId);
                String status = paymentDetails.optString("status");
                long amount = paymentDetails.optString("amount");
                long amountInRupees = amount / 100;

                JSONObject notes = paymentDetails.getJSONObject("notes");
                String paymentType = notes.optString("type");

                if(!"captured".equalsIgnoreCase(status)){
                    return  false;
                }

                if (paymentType.equals(PaymentType.MEMBERSHIP.toString())){
                    String  planCode = notes.optString("plan");
                    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getBySuncriptionPlanCode(planCode);
                    return amountInRupees == subscriptionPlan.getPrice();
                } else if (paymentType.equals(PaymentType.FINE.toString())) {
                    Long findId = notes.getLong("finde_id");
                }

                return false;
            }catch (Exception e){
                log.error("Error veriying Razorpay payment:{}",e.getMessage(),e);
                return false;
            }
        }
    }
}
