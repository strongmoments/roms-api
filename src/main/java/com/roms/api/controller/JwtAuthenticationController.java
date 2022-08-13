package com.roms.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roms.api.model.JwtRequest;
import com.roms.api.model.JwtResponse;
import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import com.roms.api.service.JwtUserDetailsService;
import com.roms.api.service.UserRolesMapService;
import com.roms.api.service.UserService;
import com.roms.api.utils.JwtTokenUtil;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolesMapService userRolesMapService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${ROLE_ADMIN}")
    private String adminMenu;

    @Value("${ROLE_EMPLOYEE}")
    private String employeeMenu;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String userNameWithOrgid = authenticationRequest.getUsername()+":"+authenticationRequest.getOrgId();
        authenticate(userNameWithOrgid, authenticationRequest.getPassword());

        Map<String,Object> response = new HashMap<>();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userNameWithOrgid);
        Users userModel = userService.findByUsername(authenticationRequest.getUsername(), authenticationRequest.getOrgId()).get();
        UserRolesMap userRolesMap =  userRolesMapService.findAllByUserId(userModel.getId()).get(0);
        Gson g = new Gson();
        final String token = jwtTokenUtil.generateToken(userDetails,authenticationRequest.getOrgId());
        response.put("token", token);
        if("ROLE_ADMIN".equalsIgnoreCase(userRolesMap.getRoleId().getName())){
            response.put("menus",g.fromJson(adminMenu,Map.class) );
        }
        if("ROLE_EMPLOYEE".equalsIgnoreCase(userRolesMap.getRoleId().getName())){
            response.put("menus",g.fromJson(employeeMenu,Map.class)  );
        }
        response.put("userDetail", userModel.getEmployeId());
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String getAppVersion() throws IOException, XmlPullParserException {

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        StringBuilder sb = new StringBuilder();
        sb.append(model.getArtifactId());
        sb.append("\n");
        sb.append(model.getVersion());

        return sb.toString();

    }
}