package com.roms.api.service;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.roms.api.model.UserRolesMap;
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
     @Autowired
     private  UserRolesMapService userRolesMapService;


    @Override
    public UserDetails loadUserByUsername(String userNameWithOrgId) throws UsernameNotFoundException {
        String userAndOrg[] = userNameWithOrgId.split(":");
        String username = userAndOrg[0];
        String orgId = userAndOrg[1];
        Optional<Users> userDetails = userService.findByUsername(username,orgId);

        if (!userDetails.isEmpty()) {
            Users  user = userDetails.get();
             List<UserRolesMap> rolse = userRolesMapService.findAllByUserId(user.getId());

             List<String> roleNames = rolse.stream().map(rolsemap->  rolsemap.getRoleId().getName()).collect(Collectors.toList());
            return  new  User(user.getUserId(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),Collections.singletonList(new SimpleGrantedAuthority(roleNames.get(0))));
            //return new User(user.getUserId(), user.getApppassword(),  Collections.singletonList(new SimpleGrantedAuthority(rolses.get(0))));

        }else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}