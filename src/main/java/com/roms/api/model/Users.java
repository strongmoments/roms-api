package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


@JsonIgnoreProperties("hibernatelazyinitializer")
@Entity
@Table(name ="users")
public class Users  extends CommonFields implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = -5534811223511068706L;


    @Column(name="userid",nullable=false)
    private String userId;

    @Column(name="authentication_type",nullable=false)
    private String authenticatonType;

    @Column(name="apppassword",nullable=false)
    private String apppassword;

    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employeId;

    @Column(name="disable_flag",nullable=false)
    private boolean disableFlag;

    @Column(name="end_date")
    public Instant endDate;

    @Column(name="password_expiry_flag")
    private boolean passwordExpiryFlag;


    @Transient
    private Roles role;

    public Users(){

    }

    public Users(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthenticatonType() {
        return authenticatonType;
    }

    public void setAuthenticatonType(String authenticatonType) {
        this.authenticatonType = authenticatonType;
    }

    public String getApppassword() {
        return apppassword;
    }

    public void setApppassword(String apppassword) {
        this.apppassword = apppassword;
    }


    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public boolean isPasswordExpiryFlag() {
        return passwordExpiryFlag;
    }

    public void setPasswordExpiryFlag(boolean passwordExpiryFlag) {
        this.passwordExpiryFlag = passwordExpiryFlag;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Employe getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Employe employeId) {
        this.employeId = employeId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return apppassword;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return disableFlag == false ? true : false;
    }
}
