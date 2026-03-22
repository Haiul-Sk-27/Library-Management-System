package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.UserException;
import Library.Management.System.com.example.Service.AuthService;
import Library.Management.System.com.example.Service.EmailService;
import Library.Management.System.com.example.configration.JwtProvider;
import Library.Management.System.com.example.domain.UserRole;
import Library.Management.System.com.example.mapper.UserMappper;
import Library.Management.System.com.example.modal.PasswordResetToken;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.UserDTO;
import Library.Management.System.com.example.payload.response.AuthResponse;
import Library.Management.System.com.example.repository.PasswordResetTokenRepository;
import Library.Management.System.com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImplementation customUserService;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    // ================= LOGIN =================
    @Override
    public AuthResponse login(String userName, String password) throws UserException {

        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(userName);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login Success");
        response.setMessage("Welcome back " + user.getFullName());
        response.setJwt(token);
        response.setUser(UserMappper.toDto(user));

        return response;
    }

    private Authentication authenticate(String userName, String password) throws UserException {

        UserDetails userDetails =
                customUserService.loadUserByUsername(userName);

        if (userDetails == null) {
            throw new UserException("User not found with email " + userName);
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public AuthResponse signUp(UserDTO req) throws UserException {

        if (userRepository.findByEmail(req.getEmail()) != null) {
            throw new UserException("Email already registered");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setFullName(req.getFullName());
        user.setLastLogin(LocalDateTime.now());
        user.setRole(UserRole.ROLE_USER);

        User savedUser = userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                null,
                Collections.singleton(
                        new SimpleGrantedAuthority(savedUser.getRole().name())
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtProvider.generateToken(auth);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Welcome, " + savedUser.getFullName());
        response.setMessage("Registration successful");
        response.setUser(UserMappper.toDto(savedUser));

        return response;
    }

    @Transactional
    public void createPasswordResetToken(String email) throws UserException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        passwordResetTokenRepository.save(resetToken);

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset",
                "Reset token: " + token
        );
    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws UserException {

        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new UserException("Invalid token"));

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new UserException("Token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}