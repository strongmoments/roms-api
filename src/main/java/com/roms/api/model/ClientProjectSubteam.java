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
public class ClientProjectSubteam extends CommonFields{

    @Column(name="guid",nullable=false,updatable=false)
    private String guid;

    @Column(name="client_idx",nullable=false)
    private String clientIdx;

    @Column(name="project_idx",nullable=false)
    private String projectIdx;

    @Column(name="team_name",nullable=false)
    private String teamName;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getClientIdx() {
        return clientIdx;
    }

    public void setClientIdx(String clientIdx) {
        this.clientIdx = clientIdx;
    }

    public String getProjectIdx() {
        return projectIdx;
    }

    public void setProjectIdx(String projectIdx) {
        this.projectIdx = projectIdx;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void save(ClientProjectSubteam model) {
    }
}
