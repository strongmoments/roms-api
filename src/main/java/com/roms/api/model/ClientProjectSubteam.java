package com.roms.api.model;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
@Entity
@Table(name="Client_project_subteams")
public class ClientProjectSubteam extends CommonFields implements  Serializable{

    @Serial
    private static final long serialVersionUID = 5786444043436593238L;

    @Column(name="team_name",nullable=false)
    private String teamName;

    @Column(name="code",nullable=false)
    private String code;

    @Column(name="wage_classification")
    private String wageClassification;

    @Column(name="wage_role")
    private String wageRole;

    @Column(name="award_type")
    private String awardType;

    @Column(name="rate")
    private String rate;


    @OneToOne()
    @JoinColumn(name = "project_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProject clientProject;

    @OneToOne()
    @JoinColumn(name = "location_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Location location;



    public ClientProjectSubteam() {
    }

    public ClientProjectSubteam(String clientProjectSubTeamId) {
        super(clientProjectSubTeamId);

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ClientProject getClientProject() {
        return clientProject;
    }

    public void setClientProject(ClientProject clientProject) {
        this.clientProject = clientProject;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWageClassification() {
        return wageClassification;
    }

    public void setWageClassification(String wageClassification) {
        this.wageClassification = wageClassification;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWageRole() {
        return wageRole;
    }

    public void setWageRole(String wageRole) {
        this.wageRole = wageRole;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }
}
