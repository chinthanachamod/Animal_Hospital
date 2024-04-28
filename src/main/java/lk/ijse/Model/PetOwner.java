package lk.ijse.Model;

public class PetOwner {
    private String petOwId;
    private String name;
    private String contactNo;

    public PetOwner(String petOwId, String name, String contactNo) {
        this.petOwId = petOwId;
        this.name = name;
        this.contactNo = contactNo;
    }

    public String getPetOwId() {
        return petOwId;
    }

    public void setPetOwId(String petOwId) {
        this.petOwId = petOwId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
