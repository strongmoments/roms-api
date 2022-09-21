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
@Table(name="Client_project_roles")
public class ClientProjectRole extends  CommonFields implements  Serializable {

    @Serial
    private static final long serialVersionUID = 5510848026981634698L;
    
    @Column(name="role_name",nullable=false,updatable=true)
    private String roleName;

    @Column(name="base_award",nullable=false)
    private double baseAward;

    @OneToOne()
    @JoinColumn(name = "project_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProject clientProject;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public double getBaseAward() {
        return baseAward;
    }

    public void setBaseAward(double baseAward) {
        this.baseAward = baseAward;
    }
}
