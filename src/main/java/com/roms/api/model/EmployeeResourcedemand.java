package com.roms.api.model;

import com.roms.api.config.ModelHashMapConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_licences")
public class EmployeeResourcedemand extends CommonFields implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hiring_manager_idx",referencedColumnName = "id")
    private Employe hiringManager;

    @Column(name="demand_type")
    private int demandType;

    @Column(name="role_name")
    private String roleName;

    @Column(name="perposedDate")
    private Instant perposedDate;

    @Column(name="description")
    private String description;

    @Column(name="type")
    private int type; // external or internal


    @Column(name="classification")
    private String classification;

    @Column(name="minimum_experience")
    private String minimumExperiecne;

    @Column(name = "skils", columnDefinition = "json")
    @Convert(converter = ModelHashMapConverter.class)
    private Map<String, List<Object>> skils;

    @OneToOne()
    @JoinColumn(name = "clientproject_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProject clientProject;

    @OneToOne()
    @JoinColumn(name = "clientproject_subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam clientProjectSubteam;

    @OneToOne()
    @JoinColumn(name = "clientproject_role_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectRole clientProjectRole;

    private String commitement;




}
