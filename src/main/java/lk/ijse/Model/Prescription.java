package lk.ijse.Model;

public class Prescription {
    private String prescId;
    private String diagnosis;
    private String gives;
    private String vetId;

    public Prescription(String prescId, String diagnosis, String gives, String vetId) {
        this.prescId = prescId;
        this.diagnosis = diagnosis;
        this.gives = gives;
        this.vetId = vetId;
    }

    public String getPrescId() {
        return prescId;
    }

    public void setPrescId(String prescId) {
        this.prescId = prescId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getGives() {
        return gives;
    }

    public void setGives(String gives) {
        this.gives = gives;
    }

    public String getVetId() {
        return vetId;
    }

    public void setVetId(String vetId) {
        this.vetId = vetId;
    }
}
