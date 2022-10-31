package com.roms.api.controller;

import com.roms.api.config.CustomPasswordEncoder;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.Users;
import com.roms.api.requestInput.UpdatePassword;
import com.roms.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/password")
public class PasswordController {

    public static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;




    @PostMapping(value = "/change", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody UpdatePassword passwordInput) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try {

            Optional<Users>  user =  userService.findByUserId();
            if(!user.isEmpty()){
                Users userModel = user.get();
                String oldPassword = userModel.getApppassword();
                if(customPasswordEncoder.matches(passwordInput.getOldPassword(),oldPassword)){
                    String newPassword  = customPasswordEncoder.encode(passwordInput.getNewPassword());
                    userModel.setApppassword(newPassword);
                    userService.updateUser(userModel);
                    response.put("status","success");
                }else{
                    response.put("error", "old_password_does_not_match");
                    response.put("status", "error");
                }
            }

        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/{employId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@PathVariable("employeeId") String employeeId) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try {

            Optional<Users>  user =  userService.findByUsername(employeeId);
            if(!user.isEmpty()){
                Users userModel = user.get();
                String newPassword = generateRandomPassword(6);

                String newPasswordEncoded  = customPasswordEncoder.encode(newPassword);
                    userModel.setApppassword(newPasswordEncoded);
                    userService.updateUser(userModel);
                    response.put("status","success");
                    response.put("newPassword",newPassword);

            }else{
                response.put("status","error");
                response.put("error","user Not found ");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public  String generateRandomPassword(int len)
    {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        return IntStream.range(0, len)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

}
