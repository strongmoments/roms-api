package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CurrentFundInput implements Serializable {

    private String abn;
    private String fundName;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String fundPhone;
    private String usi;
    private String accountName;
    private String membername;
    private String attachmentId;
    private String attachmentURL;


}
