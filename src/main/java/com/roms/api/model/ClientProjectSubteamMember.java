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
public class ClientProjectSubteamMember extends CommonFields {

    @Column(name="emp_idx",nullable=false,updatable=false)
    private String empIdx;

    @Column(name="subteam_idx",nullable=false)
    private String subteamIdx;

    @Column(name="start_date",nullable=false,updatable=false)
    private Instant startDate;

    @Column(name="end-Date",nullable = false,updatable=false)
    private Instant endDate;

    @Column(name="manager_flag",nullable=false)
    private boolean managerFlag;

    public String getEmpIdx() {
        return empIdx;
    }

    public void setEmpIdx(String empIdx) {
        this.empIdx = empIdx;
    }

    public String getSubteamIdx() {
        return subteamIdx;
    }

    public void setSubteamIdx(String subteamIdx) {
        this.subteamIdx = subteamIdx;
    }

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
}
