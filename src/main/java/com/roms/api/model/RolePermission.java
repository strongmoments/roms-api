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
@Table(name="Role_Permissions")
public class RolePermission extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = 3530023696167485223L;

    @Column(name = "role_idx", nullable = false)
    private String roleIdx;

    @Column(name = "permission_type", nullable = false)
    private String permissionType;

    @Column(name = "permissions")
    private String permissions;

    public String getRoleIdx() {
        return roleIdx;
    }

    public void setRoleIdx(String roleIdx) {
        this.roleIdx = roleIdx;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

}

