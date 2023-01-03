package com.roms.api.requestInput;

import com.roms.api.model.InspectionItems;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
public class InspectionListInput implements Serializable {

    String inspectionListId;

    List<InspectionItems> items;
}
