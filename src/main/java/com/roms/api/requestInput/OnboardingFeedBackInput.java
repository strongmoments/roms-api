package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingFeedBackInput implements Serializable {
    private String id;
    private String step;
    private String completionProgress;
    private String rating;
    private String outOf;
    private String comments;
}
