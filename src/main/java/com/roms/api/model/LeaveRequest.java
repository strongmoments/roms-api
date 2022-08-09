package com.roms.api.model;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
@Entity
@Table(name="Leave_Request")
public class LeaveRequest {

    @OneToOne()
    @JoinColumn(name="leave_type_idx",referencedColumnName="leave_type")
    @Fetch(FetchMode.SELECT)
    private Users leaveType;

    @Column(name="date_of_request",nullable=false,updatable=false)
    private Instant dateOfRequest;

    @Column(name="leave_status")
    private int leaveStatus;

    @Column(name="start_date_time",nullable=false)
    private Instant startDateTime;

    @Column(name="end_date_time",nullable=false)
    private Instant endDateTime;

    @Column(name="total_hour")
    private double totalHour;

    @Column(name="remarks")
    private String remarks;

    @Column(name="date_of_approval")
    private Instant dateOfApproval;

    @OneToOne()
    @JoinColumn(name="user_idx",referencedColumnName="user")
    @Fetch(FetchMode.SELECT)
    private Users userId;

    public Users getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(Users leaveType) {
        this.leaveType = leaveType;
    }

    public Instant getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Instant dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
