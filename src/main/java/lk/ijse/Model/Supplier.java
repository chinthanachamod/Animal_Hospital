package lk.ijse.Model;

public class Supplier {
    private String suppId;
    private String name;
    private String contactNo;

    public Supplier(String suppId, String name, String contactNo) {
        this.suppId = suppId;
        this.name = name;
        this.contactNo = contactNo;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
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
