package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name="employment_recommendation")
public class EmploymentRecommendation extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -8004799331285331949L;

    @OneToOne()
    @JoinColumn(name = "from_subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam fromSubteamIdx;


    @OneToOne()
    @JoinColumn(name = "to_subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam toSubteamIdx;

    @OneToOne()
    @JoinColumn(name = "demand_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private EmployeeResourcedemand demandIdx;

    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employeeIdx;

    @Column(name="from_rate")
    private Double fromRate;

    @Column(name="to_rate")
    private Double toRate;

    @Column(name="requested_date")
    private Instant requestedDate;

    @Column(name="subteam_join_date")
    private Instant subteamJoinDate;

    @Column(name="status")
    private Integer status;

    @Column(name="jobFlag")
    private Integer jobFlag;

    @OneToOne()
    @JoinColumn(name = "initiatedby",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe initiatedBy;

    @OneToOne()
    @JoinColumn(name = "acceptedby",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe acceptedBy;

    public ClientProjectSubteam getFromSubteamIdx() {
        return fromSubteamIdx;
    }

    public void setFromSubteamIdx(ClientProjectSubteam fromSubteamIdx) {
        this.fromSubteamIdx = fromSubteamIdx;
    }

    public ClientProjectSubteam getToSubteamIdx() {
        return toSubteamIdx;
    }

    public void setToSubteamIdx(ClientProjectSubteam toSubteamIdx) {
        this.toSubteamIdx = toSubteamIdx;
    }

    public Employe getEmployeeIdx() {
        return employeeIdx;
    }

    public void setEmployeeIdx(Employe employeeIdx) {
        this.employeeIdx = employeeIdx;
    }

    public Double getFromRate() {
        return fromRate;
    }

    public void setFromRate(Double fromRate) {
        this.fromRate = fromRate;
    }

    public Double getToRate() {
        return toRate;
    }

    public void setToRate(Double toRate) {
        this.toRate = toRate;
    }

    public Instant getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Instant requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Instant getSubteamJoinDate() {
        return subteamJoinDate;
    }

    public void setSubteamJoinDate(Instant subteamJoinDate) {
        this.subteamJoinDate = subteamJoinDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getJobFlag() {
        return jobFlag;
    }

    public void setJobFlag(Integer jobFlag) {
        this.jobFlag = jobFlag;
    }

    public Employe getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(Employe initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public Employe getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(Employe acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public EmployeeResourcedemand getDemandIdx() {
        return demandIdx;
    }

    public void setDemandIdx(EmployeeResourcedemand demandIdx) {
        this.demandIdx = demandIdx;
    }
}
