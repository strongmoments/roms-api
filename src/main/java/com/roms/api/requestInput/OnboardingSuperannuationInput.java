package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OnboardingSuperannuationInput implements Serializable {
     private String id;
     private boolean superFundNow;
     private String step;
     private String completionProgress;

}
