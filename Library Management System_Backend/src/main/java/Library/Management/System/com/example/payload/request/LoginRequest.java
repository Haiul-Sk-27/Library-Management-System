package Library.Management.System.com.example.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "username is required")
    private String userName;
    @NotNull(message = "Password is required")
    private String password;
}
