package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AddressInput implements Serializable {

    private String address;
    private String  suburb;
    private String  state;
    private String  postcode;
    private int  addressType;


}
