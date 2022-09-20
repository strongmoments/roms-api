package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name ="employee_resignation")
public class EmployeeResignation extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -1974572915327788271L;

    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employee;

    @Column(name="apply_date",nullable=false,updatable=false)
    private Instant applyDate;

    @Column(name="date_of_approval")
    private Instant dateOfApproval;

    @Column(name="last_working_date",nullable=false,updatable=false)
    private Instant lastWorkingDate;

    @Transient
    private String strLastWorkingDate;

    @Column(name="status")
    private int status;

    @OneToOne()
    @JoinColumn(name="approver_idx",referencedColumnName="id")
    @Fetch(FetchMode.SELECT)
    private Employe approver;

    @Column(name="reason")
    private String reason;

    @Column(name="comment")
    private String comment;

    @Column(name = "profile_image")
    private byte[] employeeImage;

    @Column(name = "signature")
    private byte[] signature;

    public Employe getEmployee() {
        return employee;
    }

    public void setEmployee(Employe employee) {
        this.employee = employee;
    }

    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public Instant getDateOfApproval() {
        return dateOfApproval;
    }

    public void setDateOfApproval(Instant dateOfApproval) {
        this.dateOfApproval = dateOfApproval;
    }

    public Instant getLastWorkingDate() {
        return lastWorkingDate;
    }

    public void setLastWorkingDate(Instant lastWorkingDate) {
        this.lastWorkingDate = lastWorkingDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Employe getApprover() {
        return approver;
    }

    public void setApprover(Employe approver) {
        this.approver = approver;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getEmployeeImage() {
        return employeeImage;
    }

    public void setEmployeeImage(byte[] employeeImage) {
        this.employeeImage = employeeImage;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getStrLastWorkingDate() {
        return strLastWorkingDate;
    }

    public void setStrLastWorkingDate(String strLastWorkingDate) {
        this.strLastWorkingDate = strLastWorkingDate;
    }
}
