package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="inspection_list" ,uniqueConstraints = { @UniqueConstraint(name = "uniqueInspectionList", columnNames = { "asset_type_idx", "asset_class_idx", "make","model" }) })
public class InspectionLists extends CommonFields implements Serializable {

    @Column(name="list_name")
    private String inspectionListName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_idx",referencedColumnName = "id")
    private AssetType assetType ;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_class_idx",referencedColumnName = "id")
    private AssetClass assetClass ;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    /*@OneToMany(mappedBy="inspectionList", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<InspectionListMapping> inspectionList;
*/

}
