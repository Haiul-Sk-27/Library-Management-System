package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.modal.Payment;
import Library.Management.System.com.example.payload.dto.PaymentDTO;

public class PaymentMapper {

    public Payment toDTO(Payment payment){
        if(payment == null){
            return null;
        }

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());

        //user information
        if(payment.getUser() != null){
            dto.setUserId(payment.getUser().getId());
            dto.setUserName(payment.getUser().getFullName());
            dto.setUserEmail(payment.getUser().getEmail());
        }

        //Book Loan
        if(payment.getBookLoan()!= null){
            dto.setBookLoanId(payment.getBookLoan().getId());
        }

        //subscription information
        if(payment.getSubscription() != null){
            dto.setSubscriptionId(payment.getSubscription().getId());
        }

        dto.setPaymentType(payment.getPaymentType());
        dto.setStatus(payment.getStatus());
        dto.setGateway(payment.getGateway());
        dto.setAmount(payment.getAmout());
        dto.setTranstionId(payment.getTransactionId());
        dto.setGatewayPaymentId(payment.getGatewayPaymentId());
        dto.setGatewayOrderId(payment.getGetwayOrderId());
        dto.setDescription(payment.getDescription());
        dto.setFailureReason(payment.getFailureReason());
        dto.setInitiatedAt(payment.getInitiatedAt());
        dto.setCompletedAt(payment.getCompletedAt());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());

        return  dto;
    }
}
