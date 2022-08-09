package com.roms.api.model;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
@Entity
@Table(name="Leave_Details")
public class LeaveDetails {
    @OneToOne()
    @JoinColumn(name="leave_type_idx",referencedColumnName="leave_type")
    @Fetch(FetchMode.SELECT)
    private Users leaveType;

    @Column(name="used_leave",nullable=false,updatable=false)
    private String usedLeave;

    @Column(name="balance_Leave")
    private double balanceLeave;

    @OneToOne()
    @JoinColumn(name = "user_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users userId;

    public Users getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(Users leaveType) {
        this.leaveType = leaveType;
    }

    public String getUsedLeave() {
        return usedLeave;
    }

    public void setUsedLeave(String usedLeave) {
        this.usedLeave = usedLeave;
    }

    public double getBalanceLeave() {
        return balanceLeave;
    }

    public void setBalanceLeave(double balanceLeave) {
        this.balanceLeave = balanceLeave;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
}
