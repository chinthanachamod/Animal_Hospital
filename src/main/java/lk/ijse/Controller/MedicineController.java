package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.Medicine;
import lk.ijse.repository.MedicineRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicineController {
    public AnchorPane MainAnchorpane;
    public TextField txtMedId;
    public TextField txtMedDesc;
    public TextField txtMedQty;
    public TableView<Medicine> tblMedicine;
    public TextField txtMedPrice;
    public TableColumn<?,?> colMed;
    public TableColumn<?,?> colDesc;
    public TableColumn<?,?> colQty;
    public TableColumn<?,?> colPrice;

    public void initialize(){
        setCellFactory();
        loadAllMedicines();

        tblMedicine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            Medicine selectedItem = tblMedicine.getSelectionModel().getSelectedItem();
            txtMedId.setText(selectedItem.getMedId());
            txtMedDesc.setText(selectedItem.getDescription());
            txtMedQty.setText(selectedItem.getQty());
            txtMedPrice.setText(String.valueOf(selectedItem.getPrice()));
        });
    }

    private void loadAllMedicines() {
        try {
            List<Medicine> medicineList = MedicineRepo.getAll();
            ObservableList<Medicine> observableList = FXCollections.observableArrayList(medicineList);
            tblMedicine.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellFactory() {
        colMed.setCellValueFactory(new PropertyValueFactory<>("medId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void onMedicineBackClick(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Stage stage = (Stage) MainAnchorpane.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtMedId.clear();
        txtMedDesc.clear();
        txtMedQty.clear();
        txtMedPrice.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String medId = txtMedId.getText();
        try {
            boolean isDeleted = MedicineRepo.delete(medId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine Deleted Successfully!").show();
                loadAllMedicines();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Medicine!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Medicine: " + e.getMessage()).show();
        }

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String medId = txtMedId.getText();
        String medDesc = txtMedDesc.getText();
        String medQty = txtMedQty.getText();
        double medPrice = Double.parseDouble(txtMedPrice.getText());
        Medicine medicine = new Medicine(medId, medDesc, medQty, medPrice);
        try {
            boolean isSaved = MedicineRepo.save(medicine);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine Saved Successfully!").show();
                loadAllMedicines();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Medicine!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving Medicine: " + e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String medId = txtMedId.getText();
        String medDesc = txtMedDesc.getText();
        String medQty = txtMedQty.getText();
        double medPrice = Double.parseDouble(txtMedPrice.getText());
        Medicine medicine = new Medicine(medId, medDesc, medQty, medPrice);
        try {
            boolean isUpdated = MedicineRepo.update(medicine);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine Updated Successfully!").show();
                loadAllMedicines();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Medicine!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Medicine: " + e.getMessage()).show();
        }
    }
}