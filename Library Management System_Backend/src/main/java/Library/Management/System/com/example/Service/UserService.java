package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

    public User getCurrentUser() throws Exception;
    public List<UserDTO> getAllusers();
}
