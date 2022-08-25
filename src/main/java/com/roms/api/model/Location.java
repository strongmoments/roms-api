package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="location")
public class Location extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -4590696208175081666L;

    @Column(name="code",nullable=false)
    private String code;

    @Column(name="description",nullable=false)
    private String description;

    @Column(name="address")
    private String address;

    @Column(name="geocode")
    private String geoCdoe;

    @OneToOne()
    @JoinColumn(name = "location_type_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private LocationType locationType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeoCdoe() {
        return geoCdoe;
    }

    public void setGeoCdoe(String geoCdoe) {
        this.geoCdoe = geoCdoe;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
}
