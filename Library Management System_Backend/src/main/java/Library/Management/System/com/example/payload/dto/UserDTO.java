package Library.Management.System.com.example.payload.dto;

import Library.Management.System.com.example.domain.AuthProvider;
import Library.Management.System.com.example.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String email;
    private String fullName;
    private UserRole role;
    private String phone;
    private String password;
    private String userName;
    private LocalDateTime lastLogin;
}
