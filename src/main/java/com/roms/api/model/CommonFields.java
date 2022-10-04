package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@MappedSuperclass
@JsonIgnoreProperties
public abstract  class CommonFields {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    public String id;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "org_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Organisation organisation;

    @Column(name = "create_date")
    public Instant createDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by",referencedColumnName = "id")
    private Users createBy;

    @Column(name = "last_update_date")
    public Instant lastUpdateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by",referencedColumnName = "id")
    private Users updateBy;


    public CommonFields() {
    }
    public CommonFields(String id) {
        this.id =id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public Organisation getOrganisation() {

        if(organisation == null){
            Map<String,String> loggedInUserDetails =(Map<String,String>) SecurityContextHolder.getContext().getAuthentication().getDetails();
            return new Organisation(loggedInUserDetails.get("orgId"));
        }
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {

        if(organisation == null){
            Map<String,String> loggedInUserDetails =(Map<String,String>) SecurityContextHolder.getContext().getAuthentication().getDetails();
            organisation = new Organisation((loggedInUserDetails.get("orgId")));
        }
        this.organisation = organisation;
    }


    public Instant getCreateDate() {
        return createDate == null ? Instant.now() : createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    @JsonIgnore
    public Users getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Users createBy) {
        this.createBy = createBy;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonIgnore
    public Users getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Users updateBy) {
        this.updateBy = updateBy;
    }
}
