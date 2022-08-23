package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="location_type")
public class LocationType extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 8914464453632841474L;

    @Column(name="code",nullable=false)
    private String code;

    @Column(name="description",nullable=false)
    private String description;

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
}
