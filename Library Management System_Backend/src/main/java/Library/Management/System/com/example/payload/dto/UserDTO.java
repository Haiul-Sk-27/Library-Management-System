package Library.Management.System.com.example.payload.dto;

import Library.Management.System.com.example.domain.AuthProvider;
import Library.Management.System.com.example.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "Full name is required")
    private String fullName;
    private UserRole role;
    private String phone;
    @NotNull(message = "Password is required")
    private String password;
    private String userName;
    private LocalDateTime lastLogin;
}
