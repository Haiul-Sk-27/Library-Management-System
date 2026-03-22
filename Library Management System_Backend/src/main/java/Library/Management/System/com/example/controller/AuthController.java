package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Exception.UserException;
import Library.Management.System.com.example.Service.AuthService;
import Library.Management.System.com.example.payload.dto.UserDTO;
import Library.Management.System.com.example.payload.request.LoginRequest;
import Library.Management.System.com.example.payload.request.ResetPasswordRequest;
import Library.Management.System.com.example.payload.response.ApiResponse;
import Library.Management.System.com.example.payload.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUpHandler(@RequestBody @Valid UserDTO req) throws UserException {
        AuthResponse res = authService.signUp(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody @Valid LoginRequest req
    ) throws UserException {
        AuthResponse res =authService.login(req.getUserName(),req.getPassword());
        return  ResponseEntity.ok(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) throws UserException {
        String email = request.get("email");
        authService.createPasswordResetToken(email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset token sent to email: " + email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse>resetPassword(
            @RequestBody ResetPasswordRequest req
            ) throws UserException {
        authService.resetPassword(req.getToken(),req.getPassword());
        ApiResponse res = new ApiResponse("Password reset succesfull",true);
        return ResponseEntity.ok(res);
    }

}
