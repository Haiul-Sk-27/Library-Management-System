package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.UserException;
import Library.Management.System.com.example.Service.AuthService;
import Library.Management.System.com.example.Service.EmailSerivce;
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
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static jdk.jpackage.internal.util.CompositeProxy.build;

@Service
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(String userName, String password) {
        Authentication authentication = authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<?extends GrandAuthority> authorities = authentication.getAuthorites();
        String role = authorities.iterator().next(),getAuthorites();
        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(userName);

        //Update lastLogin
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login success");
        response.setMessage("Welcome Back"+userName);
        response.setJwt(token);
        response.setUser(UserMappper.toDto(user));
        return response;
    }

    private Authentication authenticate(String userName, String password) throws UserException {

        userDetails userDetails = customUserSetviceImplementation.loadUserByUserName(userName);
        if(userDetails == null){
            throw new UserException("user not found with email "+password);
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword)){
            throw new UserException("Password not match");
        }
        return new UserPasswordAuthentication(email,null,userDetails.getAuthorities)
    }

    @Override
    public AuthResponse signUp(UserDTO req) throws UserException {

        User user = userRepository.findByEmail(req.getEmail());
        if(user == null){
            throw  new UserException("Email alredy registered");
        }
        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setPassword(passwordEncoder.encoded(req.getPassword()));
        createdUser.setPhone((req.getPhone()));
        createdUser.setFullName(req.getFullName());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setRole(UserRole.ROLE_USER);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UserNamePasswordAuthenticationToken(
                savedUser.getEmail(),savedUser.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtProvider.generateToken(auth);
        AuthResponse response = new AuthResponse();

        response.setJwt(jwt);
        response.setTitle("Welcome, "+createdUser.getFullName());
        response.setMessage("Register success");
        response.setUser(UserMappper.toDto(savedUser));
        return response;
    }

    @Transactional
    public void createPasswordResetToken(String email) {

        String frontendUrl = " ";
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User Not found with given email");
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expriryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        PasswordResetTokenRepository.save(resetToken);
        String resetLink = frontendUrl+token;
        String subject = "Password Reset Request";
        String body = "You request to reset your password.Use this link(Valid 5 minutes) :" + resetLink;

        //Sent in email.

        EmailSerivce.sendEmail(user.getEmail(),subject,body);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = PasswordResetTokenRepository.findByToken(token)
                orElseThrow(
                        ()->new Exception("Token not found");
                );
        if(resetToken.isExpired()){
            PasswordResetTokenRepository.delete(resetToken);
            throw new Exception("token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        PasswordResetTokenRepository.delete(resetToken);
    }
}
