package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="Employees")
public class EmployeeDevices extends CommonFields implements Serializable {

    @Serial
    private static final long serialVersionUID = -2831435754290270747L;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "emi_no")
    private String emiNo;

    @Column(name = "notification_id")
    private String notificationDeviceToken;

    @Column(name = "device_id")
    private String deviceId;



    @OneToOne()
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Employe employe;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getEmiNo() {
        return emiNo;
    }

    public void setEmiNo(String emiNo) {
        this.emiNo = emiNo;
    }


    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNotificationDeviceToken() {
        return notificationDeviceToken;
    }

    public void setNotificationDeviceToken(String notificationDeviceToken) {
        this.notificationDeviceToken = notificationDeviceToken;
    }
}
