package spring4all.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring4all.entity.UserEntity;
import spring4all.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getByUsername(username);
        if (userEntity == null){
            throw new UsernameNotFoundException("no user");
        }
        return User.withDefaultPasswordEncoder().username(userEntity.getUsername()).password(userEntity.getPassword()).authorities(userEntity.getRoles()).build();
    }

}
