package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingLicenceInput implements Serializable {
    private String id;
    private String step;
    private String completionProgress;
    private String licenceImageId;
    private String licenceImageURL;
    private String licenceBackImageId;
    private String licenceBackImageURL;
    private String issuedIn;
    private String licenceNumber;
    private String expiryDate;
    private int declarationStatus;
    private  String signatureImageId;
    private  String signatureImageURL;

}
