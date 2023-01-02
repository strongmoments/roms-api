package com.roms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="inpection_list_mapping")
public class InspectionListMapping extends CommonFields implements Serializable {

    @OneToOne()
    @JoinColumn(name = "inpection_list_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private InspectionLists inspectionList;

    @OneToOne()
    @JoinColumn(name = "inpection_item_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private InspectionItems inspectionItems;

}
