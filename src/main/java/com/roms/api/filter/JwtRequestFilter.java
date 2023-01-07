package com.roms.api.filter;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roms.api.config.CustomPasswordEncoder;
import com.roms.api.constant.Constant;
import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import com.roms.api.service.UserRolesMapService;
import com.roms.api.service.UserService;
import com.roms.api.utils.JwtTokenUtil;
import com.roms.api.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;





@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolesMapService userRolesMapService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        String orgId = null;
        String password = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                orgId = jwtTokenUtil.getOrganisationIdFromToken(jwtToken);
                password = jwtTokenUtil.getPasswordFromToken(jwtToken);

            } catch (IllegalArgumentException e) {
                //System.out.println("Unable to get JWT Token");
            } catch (Exception e) {
               // System.out.println("JWT Token has expired");
            }
        } /*else {
            logger.warn("JWT Token does not begin with Bearer String");
        }*/

        // Once we get the token validate it.
        if (username != null && orgId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username+":"+orgId);

            // CHECK IF PASSWORD HAS BEEN CHANGED
            //if()

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                Users userModel = userService.findByUsername(username,orgId).get();
                // check if user has expired
               /* if(Instant.now().isAfter(userModel.getEmployeId().getEndDate() == null ? Instant.now() : userModel.getEmployeId().getEndDate())){

                   throw new AccountExpiredException("user expired");
                    //return new ResponseEntity<>(response, HttpStatus.PERMANENT_REDIRECT);

                }*/

               UserRolesMap userRolesMap =  userRolesMapService.findAllByUserId(userModel.getId()).get(0);
                Map<String, Object> loggedInUserDetails  = new HashMap<>();
                userModel.setRole(userRolesMap.getRoleId());
                loggedInUserDetails.put(Constant.USER_ID,userModel);
                loggedInUserDetails.put(Constant.ORG_ID,orgId);
                loggedInUserDetails.put("role",userRolesMap.getRoleId().getName());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(loggedInUserDetails);
                       // .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}