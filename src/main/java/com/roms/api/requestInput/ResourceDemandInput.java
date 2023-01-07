package com.roms.api.requestInput;


import com.roms.api.model.Employe;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
public class ResourceDemandInput {

    private String hiringManagerId;
    private int demandType;
    private String profileRole; // like Profile name
    private String perposedDate; //
    private String description;
    private int type; // external or internal
    private String minimumExperiecne;
    private Map<String, Object> skils;
    private String clientProjectName;
    private String clientProjectNameId;
    private String locationId;
    private String locationName;
    private String contractName;
    private String contractId;
    private String clientId;
    private String clientName;
    private String clientProjectRoleId;
    private String clientProjectRoleName;
    private String rate;
    private String clientProjectSubteamId;
    private String clientProjectSubteamName;
    private String commitment;

    private String wageClassification;
    private String wageRole;
    private String awardType;


}
