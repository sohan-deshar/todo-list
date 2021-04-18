package db.mongo.todolist.configs.services;

import db.mongo.todolist.models.User;
import db.mongo.todolist.services.interfaces.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = this.userService.findByUsername(username);

        if(optionalUser.isPresent()){
            return new UserDetailsImpl(optionalUser.get());
        } else {
            throw new UsernameNotFoundException(username + " not available in our database");
        }
    }
}
