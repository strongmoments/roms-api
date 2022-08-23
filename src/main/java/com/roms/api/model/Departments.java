package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="departments")
public class Departments extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -705270803406126780L;

    @Column(name="code",nullable=false)
    private String code;

    @Column(name="description",nullable=false)
    private String description;

    @Column(name="notes")
    private String notes;

    @OneToOne()
    @JoinColumn(name = "location_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Location location;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
