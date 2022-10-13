package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingCompleteInput implements Serializable {

    private String id;
    private String step;
    private String completionProgress;
    private String firstName;
    private String lastName;
    private String profileImageURL;
}
