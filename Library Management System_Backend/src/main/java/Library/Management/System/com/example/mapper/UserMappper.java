package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.payload.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMappper {

    public static UserDTO toDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhone(user.getPhone());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static List<UserDTO> userDTOList(List<User> users){
        return users.stream()
                .map(UserMappper::toDto)
                .collect(Collectors.toList());
    }

    public static Set<UserDTO> toDTOSet(Set<User> users){
        return users.stream()
                .map(UserMappper::toDto)
                .collect(Collectors.toSet());
    }

    public static User toEntity(UserDTO userDTO){
        User createdUser = new User();
        createdUser.setEmail(userDTO.getEmail());
        createdUser.setPassword(userDTO.getPassword());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setPhone(userDTO.getPhone());
        createdUser.setFullName(userDTO.getFullName());
        createdUser.setRole(userDTO.getRole());
        return createdUser;
    }
}
