package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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

    @OneToOne()
    @JoinColumn(name = "org_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Organisation organisation;

    @Column(name = "create_date")
    public Instant createDate;


    public CommonFields() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Instant getCreateDate() {
        return createDate == null ? Instant.now() : createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }
}
