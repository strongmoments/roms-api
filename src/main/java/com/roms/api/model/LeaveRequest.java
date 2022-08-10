package com.roms.api.model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.roms.api.utils.InstantConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name="Leave_Request")
public class LeaveRequest extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 1935968838537925917L;

    @OneToOne()
    @JoinColumn(name="leave_type_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private LeaveType leaveType;

    @Column(name="apply_date",nullable=false,updatable=false)
    private Instant applyDate;

    @Column(name="leave_status")
    private int leaveStatus;

    @Column(name="start_date_time",nullable=false)
    private Instant startDateTime;

    @Column(name="end_date_time",nullable=false)
    private Instant endDateTime;

    @Transient
    private String strStartDateTime;
    @Transient
    private String strEndDateTime;


    @Column(name="total_hour")
    private double totalHour;

    @Column(name="leave_reason")
    private String leaveReason;

    @Column(name = "reviewer_remark", columnDefinition = "TEXT")
    private String reviewerRemark;

    @Column(name="date_of_approval")
    private Instant dateOfApproval;

    @OneToOne()
    @JoinColumn(name="employe_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private Employe employe;

    @OneToOne()
    @JoinColumn(name="approver_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private Employe approver;


    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(double totalHour) {
        this.totalHour = totalHour;
    }

    public Instant getDateOfApproval() {
        return dateOfApproval;
    }

    public void setDateOfApproval(Instant dateOfApproval) {
        this.dateOfApproval = dateOfApproval;
    }


    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getStrStartDateTime() {
        return strStartDateTime;
    }

    public void setStrStartDateTime(String strStartDateTime) {
        this.strStartDateTime = strStartDateTime;
    }

    public String getStrEndDateTime() {
        return strEndDateTime;
    }

    public void setStrEndDateTime(String strEndDateTime) {
        this.strEndDateTime = strEndDateTime;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Employe getApprover() {
        return approver;
    }

    public void setApprover(Employe approver) {
        this.approver = approver;
    }
}
