package Library.Management.System.com.example.repository;

import Library.Management.System.com.example.modal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepossitory extends JpaRepository<Payment,Long> {
}
