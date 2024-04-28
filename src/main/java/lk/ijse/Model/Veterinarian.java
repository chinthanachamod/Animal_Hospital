package lk.ijse.Model;

public class Veterinarian {
    private String vetId;
    private String name;
    private int yrsOfExperience;

    public Veterinarian(String vetId, String name, int yrsOfExperience) {
        this.vetId = vetId;
        this.name = name;
        this.yrsOfExperience = yrsOfExperience;
    }

    public String getVetId() {
        return vetId;
    }

    public void setVetId(String vetId) {
        this.vetId = vetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYrsOfExperience() {
        return yrsOfExperience;
    }

    public void setYrsOfExperience(int yrsOfExperience) {
        this.yrsOfExperience = yrsOfExperience;
    }
}
