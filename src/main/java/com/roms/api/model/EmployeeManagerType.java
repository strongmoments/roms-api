package com.roms.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name ="employee_manager_type")
public class EmployeeManagerType extends CommonFields implements Serializable {

    @Column(name="type")
    private String type;

    @Column(name="code")
    private String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
