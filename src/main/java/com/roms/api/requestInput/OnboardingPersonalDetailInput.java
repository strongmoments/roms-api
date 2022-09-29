package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OnboardingPersonalDetailInput implements Serializable {
    public String id;
    public String employeeNo;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    private String middleName;
    private String nickName;
    private String phoneticName;
    private String jobTitle;
    private String pronoun;
    private String salut;
    private String birthdate;
    private String gender;
    private String startDate;
    private String endDate;
    private String step;
    private String completionProgress;
    private int indigenousFlag;
    private List<AddressInput> addresses;
    private MultipartFile profileImage;



}
