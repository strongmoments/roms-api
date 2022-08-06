package com.roms.api.service;


import java.util.Collections;
import java.util.Optional;


import com.roms.api.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

     @Autowired
     private UserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> userDetails = userService.findByUsername(username);

        if (!userDetails.isEmpty()) {
            Users  user = userDetails.get();
           // return  new  User(user.getUserName(), user.getPassword(), boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority > authorities)
            return new User(user.getUserId(), user.getApppassword(),  Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())));

        }else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}