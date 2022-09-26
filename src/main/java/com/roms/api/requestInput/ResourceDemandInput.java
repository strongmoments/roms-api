package com.roms.api.requestInput;

import com.roms.api.config.ModelHashMapConverter;
import com.roms.api.model.ClientProject;
import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.Employe;
import lombok.Builder;
import lombok.Data;
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
public class ResourceDemandInput {

    private String hiringManagerId;
    private int demandType;
    private String profileRole; // like Profile name
    private String perposedDate; //
    private String description;
    private int type; // external or internal
    private String classification;
    private String minimumExperiecne;
    private Map<String, List<String>> skils;
    private String clientProjectName;
    private String clientProjectNameId;
    private String locationId;
    private String locationName;
    private String contract;
    private String clientProjectRoleId;
    private String clientProjectRoleName;
    private Double rate;
    private String clientProjectSubteamId;
    private String clientProjectSubteamName;
    private String commitment;

    public String getHiringManagerId() {
        return hiringManagerId;
    }

    public void setHiringManagerId(String hiringManagerId) {
        this.hiringManagerId = hiringManagerId;
    }

    public int getDemandType() {
        return demandType;
    }

    public void setDemandType(int demandType) {
        this.demandType = demandType;
    }

    public String getProfileRole() {
        return profileRole;
    }

    public void setProfileRole(String profileRole) {
        this.profileRole = profileRole;
    }

    public String getPerposedDate() {
        return perposedDate;
    }

    public void setPerposedDate(String perposedDate) {
        this.perposedDate = perposedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getMinimumExperiecne() {
        return minimumExperiecne;
    }

    public void setMinimumExperiecne(String minimumExperiecne) {
        this.minimumExperiecne = minimumExperiecne;
    }

    public Map<String, List<String>> getSkils() {
        return skils;
    }

    public void setSkils(Map<String, List<String>> skils) {
        this.skils = skils;
    }

    public String getClientProjectName() {
        return clientProjectName;
    }

    public void setClientProjectName(String clientProjectName) {
        this.clientProjectName = clientProjectName;
    }

    public String getClientProjectNameId() {
        return clientProjectNameId;
    }

    public void setClientProjectNameId(String clientProjectNameId) {
        this.clientProjectNameId = clientProjectNameId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getClientProjectRoleId() {
        return clientProjectRoleId;
    }

    public void setClientProjectRoleId(String clientProjectRoleId) {
        this.clientProjectRoleId = clientProjectRoleId;
    }

    public String getClientProjectRoleName() {
        return clientProjectRoleName;
    }

    public void setClientProjectRoleName(String clientProjectRoleName) {
        this.clientProjectRoleName = clientProjectRoleName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getClientProjectSubteamId() {
        return clientProjectSubteamId;
    }

    public void setClientProjectSubteamId(String clientProjectSubteamId) {
        this.clientProjectSubteamId = clientProjectSubteamId;
    }

    public String getClientProjectSubteamName() {
        return clientProjectSubteamName;
    }

    public void setClientProjectSubteamName(String clientProjectSubteamName) {
        this.clientProjectSubteamName = clientProjectSubteamName;
    }

    public String getCommitment() {
        return commitment;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }
}
