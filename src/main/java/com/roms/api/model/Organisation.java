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
public class Organisation  implements Serializable {

    @Serial
    private static final long serialVersionUID = 624293652934657492L;

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    public String id;

    @Column(name="org_name",nullable=false)
    private String orgName;

    @Column(name="tenant_expiry_date")
    private Instant teantExpiryDate;

    @OneToOne()
    @JoinColumn(name = "create_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users createBy;

    @Column(name = "create_date")
    public Instant createDate;

    @OneToOne()
    @JoinColumn(name = "update_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users updateBy;

    @Column(name="last_update_date")
    private Instant lastUpdateDate;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getTeantExpiryDate() {
        return teantExpiryDate;
    }

    public void setTeantExpiryDate(Instant teantExpiryDate) {
        this.teantExpiryDate = teantExpiryDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
