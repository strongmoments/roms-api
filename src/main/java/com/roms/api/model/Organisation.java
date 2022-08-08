package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
@Entity
@Table(name="Organisations")
public class Organisation  extends  CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 624293652934657492L;

    @Column(name="org_name",nullable=false)
    private String orgName;

    @Column(name="tenant_expiry_date")
    private Instant teantExpiryDate;



    public Organisation() {
    }

    public Organisation(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Instant getTeantExpiryDate() {
        return teantExpiryDate;
    }

    public void setTeantExpiryDate(Instant teantExpiryDate) {
        this.teantExpiryDate = teantExpiryDate;
    }

}
