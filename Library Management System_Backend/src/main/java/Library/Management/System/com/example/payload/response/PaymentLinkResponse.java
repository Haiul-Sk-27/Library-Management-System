package Library.Management.System.com.example.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;
}
