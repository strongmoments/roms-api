package com.roms.api.model;
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

    @Column(name="total_hour")
    private double totalHour;

    @Column(name="leave_reason")
    private String leaveReason;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name="date_of_approval")
    private Instant dateOfApproval;

    @OneToOne()
    @JoinColumn(name="user_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private Users userId;

    @OneToOne()
    @JoinColumn(name="approver_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private Users approver;


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

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Users getApprover() {
        return approver;
    }

    public void setApprover(Users approver) {
        this.approver = approver;
    }
}
