package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OnboardingSuperannuationInput implements Serializable {
     private String id;
     private boolean fillSuperFundNow;
     private String step;
     private String completionProgress;
     private String signatureId;
     private String signatureURL;
     private boolean paidAsperMychoice;
     private  String date ;
     private CurrentFundInput currentFund;
     private SelfFundInput selfManagedFund;


}
