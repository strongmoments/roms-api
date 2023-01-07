package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingMembershipInput implements Serializable {

    private String id;
    private String step;
    private String completionProgress;
    private boolean longServiceLeaveScheme;
    private boolean redundancyScheme;
    private String redundancySchemeName;
    private String redundancySchemeMemberShipNo;
    private String longServiceSchemeName;
    private String longServiceSchemeMemberShipNo;
}
