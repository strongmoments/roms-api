package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OnboardingTFNInput implements Serializable {

    private String id;
    private Integer taxPayerType;
    private String TFNNumber;
    private boolean haveTFN;
    private boolean claimTaxfreeThreshold;
    private boolean haveanyDebt;
    private boolean tncAcceptance;


}
