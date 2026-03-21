package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.modal.User;
import Library.Management.System.com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserServiceImplementation implements UserDetailsService{

    private final UserRepository userRepository;

    private CustomeUserServiceImaplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //jwt token
    @Override
    public userDetails loadUsername(String username) throws UserNotFoundException{

        User user = userRepository.findbyEmail(username);
        if(user = null){
            throw new UsernameNotFoundException("User not exits whith userName: "+ username);
        }

        GrandAuthority authority = new SimpleGrandAuthority(user.getRole().toString());

        Collection<GrandAuthority> authorities = Collections.singleton(authority);
        return new org.springframework.security.core.userDetails.User(
                user.getEmail(),user.getPassword(),authorities
        );

    }

}
