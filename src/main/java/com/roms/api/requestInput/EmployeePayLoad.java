package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class EmployeePayLoad {

   public String id;
    public String employeeNo;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String appliedOn;
    public String orgId;
    public  int status;




}
