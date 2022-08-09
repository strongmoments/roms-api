package com.roms.api.model;
import javax.persistence.*;
import java.time.Instant;
@Entity
@Table(name="Leave_Type")
public class LeaveType {
    @Column(name="leave_type_id",nullable=false,updatable=false)
    private long leaveTypeId;

    @Column(name="leave_name",nullable=false,updatable=false)
    private String name;

    @Column(name="leave_description",nullable=false)
    private String leaveDescription;

    @Column(name="number_days_allowed",nullable=false,updatable=false)
    private int numberDaysAllowed;

    public long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaveDescription() {
        return leaveDescription;
    }

    public void setLeaveDescription(String leaveDescription) {
        this.leaveDescription = leaveDescription;
    }

    public int getNumberDaysAllowed() {
        return numberDaysAllowed;
    }

    public void setNumberDaysAllowed(int numberDaysAllowed) {
        this.numberDaysAllowed = numberDaysAllowed;
    }
}
