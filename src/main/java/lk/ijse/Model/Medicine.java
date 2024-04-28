package lk.ijse.Model;

public class Medicine {
    private String medId;
    private String description;
    private String qty;
    private Double price;

    public Medicine(String medId, String description, String qty, Double price) {
        this.medId = medId;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
