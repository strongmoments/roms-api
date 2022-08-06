package com.roms.api.model;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name ="Employees")
public class Employe extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 8369516416956648916L;

    @Column(name = "first_name", nullable = false, updatable = false)
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
    private long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Column(name = "gender", nullable = false)
    private char gender;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "indigenous_flag", nullable = false)
    private char indigenousFlag;

    @Column(name = "staff_type_idx")
    private String staffTypeIdx;

    @Column(name = "department_idx")
    private int departmentIdx;

    @Column(name = "profile_image")
    private byte[] profileImage;

   @Column(name = "last_update_date")
    private Instant lastUpdateDate;

    @OneToOne()
    @JoinColumn(name = "create_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users createBy;

    @OneToOne()
    @JoinColumn(name = "update_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users updateBy;

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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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

    public char getIndigenousFlag() {
        return indigenousFlag;
    }

    public void setIndigenousFlag(char indigenousFlag) {
        this.indigenousFlag = indigenousFlag;
    }

    public String getStaffTypeIdx() {
        return staffTypeIdx;
    }

    public void setStaffTypeIdx(String staffTypeIdx) {
        this.staffTypeIdx = staffTypeIdx;
    }

    public int getDepartmentIdx() {
        return departmentIdx;
    }

    public void setDepartmentIdx(int departmentIdx) {
        this.departmentIdx = departmentIdx;
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

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Users getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Users createBy) {
        this.createBy = createBy;
    }

    public Users getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Users updateBy) {
        this.updateBy = updateBy;
    }
}


