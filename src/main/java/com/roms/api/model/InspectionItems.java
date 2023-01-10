package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name ="inpection_items")
public class InspectionItems extends CommonFields implements Serializable {

    @Column(name="inspection_name")
    private String inspectionName;

    @Column(name="descriptino")
    private String description;

    @Column(name="icon")
    private String icon;

    @Column(name="video_link")
    private String videoLink;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_idx",referencedColumnName = "id")
    private AssetType assetType ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_idx",referencedColumnName = "id")
    private ItemCategory itemCategory ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_class_idx",referencedColumnName = "id")
    private AssetClass assetClass ;

    @OneToMany(mappedBy="inspectionItems", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<InspectionItemAttachment> media;

    @Transient
    private  int inspectionOrder;



}
