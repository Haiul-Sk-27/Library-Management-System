package Library.Management.System.com.example.payload.dto;

import Library.Management.System.com.example.domain.AuthProvider;
import Library.Management.System.com.example.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    @NonNull(message = "email is required");
    private String email;
    @NonNull(message = "Full name is required")
    private String fullName;
    private UserRole role;
    private String phone;
    @NonNull(message = "Password is required")
    private String password;
    private String userName;
    private LocalDateTime lastLogin;
}
