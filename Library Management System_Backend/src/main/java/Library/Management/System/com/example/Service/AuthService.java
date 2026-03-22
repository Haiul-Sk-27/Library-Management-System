package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.Exception.UserException;
import Library.Management.System.com.example.payload.dto.UserDTO;
import Library.Management.System.com.example.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String userName,String password) throws UserException;
    AuthResponse signUp(UserDTO req) throws UserException;
    void createPasswordResetToken(String email) throws UserException;
    void resetPassword(String token,String newPassword) throws UserException;
}
