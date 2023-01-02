package com.roms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="inpection_list")
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


}
