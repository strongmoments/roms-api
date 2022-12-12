package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class RecommendInput {
    String id;
    String employeeId;
    String demandId;
    String resourceDemandId;
    String subTeamId;
}
