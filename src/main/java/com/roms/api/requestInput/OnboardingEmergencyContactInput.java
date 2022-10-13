package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingEmergencyContactInput implements Serializable {
    private String id;
    private String relationship;
    private String step;
    private String completionProgress;
    public String firstName;
    public String lastName;
    private String middleName;
    private String salut;
    public String email;
    public String phone;
    public String mobile;
    public AddressInput address;


}
