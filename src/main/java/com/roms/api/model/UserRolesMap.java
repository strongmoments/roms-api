package com.roms.api.model;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name="UserRolesMap")
public class UserRolesMap extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 937322494180391963L;

    @OneToOne()
    @JoinColumn(name = "user_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users userId;

    @OneToOne()
    @JoinColumn(name = "role_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Roles roleId;

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }
}
