package com.roms.api.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Roles extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -6041223701968440703L;
    @Column(name = "role")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
