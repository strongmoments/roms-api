package com.roms.api.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract  class CommonFields {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    public String id;

    @Column(name = "create_date")
    public Instant createDate;

    @Column(name = "org_idx")
    public String orgIdx;

    /*@OneToOne()
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    public Roles role;*/

    public CommonFields() {
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getOrgIdx() {
        return orgIdx;
    }

    public void setOrgIdx(String orgIdx) {
        this.orgIdx = orgIdx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
