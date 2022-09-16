package com.roms.api.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties("hibernatelazyinitializer")
@Entity
@Table(name ="Employees")
public class Employe extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 8369516416956648916L;

    @Column(name = "employee_no", nullable = false )
    private String employeeNo;

    @Column(name = "first_name", nullable = false )
    private String firstName;


    @Column(name = "middle_name", nullable = true, updatable = true)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nick_name", nullable = true, updatable = true)
    private String nickName;

    @Column(name = "phonetic_name")
    private String phoneticName;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "pronoun")
    private String pronoun;

    @Column(name = "phone")
    private String  phone;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "indigenous_flag", nullable = false)
    private boolean indigenousFlag;

    @Column(name="manager_flag")
    private boolean managerFlag;

    @OneToOne()
    @JoinColumn(name = "employee_typeIdx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private EmployeType employeType;


    @OneToOne()
    @JoinColumn(name = "department_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Departments departments;

    @Transient
    private String orgId;


    @Column(name = "profile_image")
    private byte[] profileImage;

    public  Employe(){
        
    }

    public Employe(String id) {
        super(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneticName() {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName) {
        this.phoneticName = phoneticName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPronoun() {
        return pronoun;
    }

    public void setPronoun(String pronoun) {
        this.pronoun = pronoun;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public boolean isIndigenousFlag() {
        return indigenousFlag;
    }

    public void setIndigenousFlag(boolean indigenousFlag) {
        this.indigenousFlag = indigenousFlag;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public EmployeType getEmployeType() {
        return employeType;
    }

    public void setEmployeType(EmployeType employeType) {
        this.employeType = employeType;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public boolean isManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(boolean managerFlag) {
        this.managerFlag = managerFlag;
    }
}


