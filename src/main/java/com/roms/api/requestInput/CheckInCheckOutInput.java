package com.roms.api.requestInput;

import com.roms.api.model.CheckInCheckOutHistory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CheckInCheckOutInput {

    List<CheckInCheckOutHistory> checkInCheckout;

}
