package com.roms.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roms.api.model.*;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.service.JwtUserDetailsService;
import com.roms.api.service.UserRolesMapService;
import com.roms.api.service.UserService;
import com.roms.api.utils.JwtTokenUtil;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import java.io.InputStream;
import java.time.Instant;
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

    @Value("${ROLE_SUPERVISOR}")
    private String supervisor;

    @Value("${release.version}")
    private String releaseVersion;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String userNameWithOrgid = authenticationRequest.getUsername()+":"+authenticationRequest.getOrgId();
        authenticate(userNameWithOrgid, authenticationRequest.getPassword());

        Map<String,Object> response = new HashMap<>();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userNameWithOrgid);
        Users userModel = userService.findByUsername(authenticationRequest.getUsername(), authenticationRequest.getOrgId()).get();
        // check if user has expired
        if(Instant.now().isAfter(userModel.getEmployeId().getEndDate() == null ? Instant.now() : userModel.getEmployeId().getEndDate())){

            response.put("error","user_has_expired");
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.PERMANENT_REDIRECT);

        }

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
        if("ROLE_SUPERVISOR".equalsIgnoreCase(userRolesMap.getRoleId().getName())){
            response.put("menus",g.fromJson(supervisor,Map.class)  );
        }

        response.put("userDetail", userModel.getEmployeId());
        response.put("last_login",userModel.getLastLogin() == null ? "never" :userModel.getLastLogin());
        response.put("role",userRolesMap.getRoleId().getName());
        userModel.setLastLogin(Instant.now());
        userService.updateLastLogin(userModel);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/addUserRequest", method = RequestMethod.POST)
    public ResponseEntity<?> requestUser(@RequestBody EmployeePayLoad employe) throws Exception {


        Map<String,Object> response = new HashMap<>();
        if(userService.doesUserExist(employe.getEmail(),employe.getOrgId())){
            response.put("status","error");
            response.put("error","already_exist");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String resonse  = userService.saveTemporary(employe);
        if(!"success".equalsIgnoreCase(resonse)){
            response.put("status","error");
            response.put("error",resonse);
        }else{
            response.put("status",resonse);
        }

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
        InputStream input = JwtAuthenticationController.class.getResourceAsStream("/META-INF/maven/com.roms/roms-employee/pom.xml");
        Model model = reader.read(input);
        StringBuilder sb = new StringBuilder();
        String version =  model.getArtifactId();
        if(version != null){
            version =    version.replace("roms-employee","");
        }
       sb.append(version);
        sb.append(model.getVersion());

        return sb.toString();

    }
}