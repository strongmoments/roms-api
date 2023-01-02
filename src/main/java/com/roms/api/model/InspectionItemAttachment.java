package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="inspection_item_attachments")
public class InspectionItemAttachment  extends CommonFields implements Serializable {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_item_idx",referencedColumnName = "id")
    @JsonBackReference
    private InspectionItems inspectionItems;

    @OneToOne()
    @JoinColumn(name = "digital_asset_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets digitalAssets;
}
