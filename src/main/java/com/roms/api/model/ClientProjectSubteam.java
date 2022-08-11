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

    @OneToOne()
    @JoinColumn(name = "project_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProject clientProject;

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


}
