package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "roles")
public class Roles extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -6041223701968440703L;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne()
    @JoinColumn(name = "create_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users createBy;

    @OneToOne()
    @JoinColumn(name = "update_by",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users updateBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Users createBy) {
        this.createBy = createBy;
    }

    public Users getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Users updateBy) {
        this.updateBy = updateBy;
    }
}
