package lk.ijse.Model;

import java.util.Date;

public class Appointment {
    private String appId;
    private Date date;
    private String time;
    private String petOwId;
    private String vetId;

    public Appointment(String appId, Date date, String time, String petOwId, String vetId) {
        this.appId = appId;
        this.date = date;
        this.time = time;
        this.petOwId = petOwId;
        this.vetId = vetId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPetOwId() {
        return petOwId;
    }

    public void setPetOwId(String petOwId) {
        this.petOwId = petOwId;
    }

    public String getVetId() {
        return vetId;
    }

    public void setVetId(String vetId) {
        this.vetId = vetId;
    }
}
