package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OnboardingMembershipInput implements Serializable {

    private String id;
    private String step;
    private String completionProgress;
    private boolean longServiceLeaveScheme;
    private boolean redundancyScheme;
    private String redundancyShemeName;
    private String redundancyShemeMemberShipNo;
    private String longServiceShemeName;
    private String longServiceShemeMemberShipNo;
}
