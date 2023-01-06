package com.roms.api.requestInput;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roms.api.model.Assets;
import com.roms.api.model.DigitalAssets;
import com.roms.api.model.Location;
import com.roms.api.model.PrestartDetails;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@ToString
public class PrestartInput implements Serializable {

    String id;
    private String date;
    private String startTime;
    private String endTime;
    private String assetId ;
    private String employeeSelfieImageId;
    private String  locationLatLong;
    private String  prestartLocation;
    private List<PrestartDetails> prestartDetails;
}
