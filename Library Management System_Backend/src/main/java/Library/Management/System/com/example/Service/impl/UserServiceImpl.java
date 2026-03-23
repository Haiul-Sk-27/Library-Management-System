package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.UserService;
import Library.Management.System.com.example.mapper.UserMappper;
import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.UserDTO;
import Library.Management.System.com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getCurrentUser() throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw  new Exception("user not found");
        }

        return user;
    }

    @Override
    public List<UserDTO> getAllusers() {
        List<User>  users = userRepository.findAll();
        return users.stream().map(
                UserMappper::toDto
        ).collect(Collectors.toList());
    }
}
