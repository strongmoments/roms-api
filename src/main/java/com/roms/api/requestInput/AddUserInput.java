package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddUserInput {

    public String id;
    public String employeeNo;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String orgId;
    public String departmentId;
    public String departmentName;
    public String roleId;
    public String roleName;
    public String managerId;
    public String employTypeId;
    public String employType;
    public String gender;
    public boolean isManager;
    public boolean notifyBySms;



}
