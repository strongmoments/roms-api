package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OnboardingCompleteInput implements Serializable {

    private String id;
    private String step;
    private String completionProgress;
    private String firstName;
    private String lastName;
}
