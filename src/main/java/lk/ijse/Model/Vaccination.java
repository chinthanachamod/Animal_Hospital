package lk.ijse.Model;

public class Vaccination {
    private String vaccId;
    private String description;
    private String dosage;

    public Vaccination(String vaccId, String description, String dosage) {
        this.vaccId = vaccId;
        this.description = description;
        this.dosage = dosage;
    }

    public String getVaccId() {
        return vaccId;
    }

    public void setVaccId(String vaccId) {
        this.vaccId = vaccId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
