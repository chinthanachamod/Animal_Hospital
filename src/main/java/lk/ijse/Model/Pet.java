package lk.ijse.Model;

public class Pet {
    private String petId;
    private int age;
    private String weight;
    private String breed;
    private String type;
    private String petOwId;

    public Pet(String petId, int age, String weight, String breed, String type, String petOwId) {
        this.petId = petId;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
        this.type = type;
        this.petOwId = petOwId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPetOwId() {
        return petOwId;
    }

    public void setPetOwId(String petOwId) {
        this.petOwId = petOwId;
    }
}
