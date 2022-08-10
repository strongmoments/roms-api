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
@Table(name="Client_project_subteam_members")
public class ClientProjectSubteamMember extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = -7936996574004918347L;
    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employee;


    @OneToOne()
    @JoinColumn(name = "subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam clientProjectSubteam;


    @Column(name="start_date",nullable=false,updatable=false)
    private Instant startDate;

    @Column(name="end-Date",nullable = false,updatable=false)
    private Instant endDate;

    @Column(name="manager_flag",nullable=false)
    private boolean managerFlag;



    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public boolean isManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(boolean managerFlag) {
        this.managerFlag = managerFlag;
    }

    public Employe getEmployee() {      return employee;  }

    public void setEmployee(Employe employee) {     this.employee = employee;   }

    public ClientProjectSubteam getClientProjectSubteam() {
        return clientProjectSubteam;
    }

    public void setClientProjectSubteam(ClientProjectSubteam clientProjectSubteam) {
        this.clientProjectSubteam = clientProjectSubteam;
    }
}
