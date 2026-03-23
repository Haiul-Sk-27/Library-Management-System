package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.domain.UserRole;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run (String... args){
        initializedAdminUser();
    }
    private void initializedAdminUser(){
        String adminEmail = "Haiulsk017@gamil.com";
        String adminPassword = "HaiulSk@2003";
        if(userRepository.findByEmail(adminEmail)==null){
            User user = User.builder()
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .fullName("Code with haiul")
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            User admin = userRepository.save(user);
        }
    }
}
