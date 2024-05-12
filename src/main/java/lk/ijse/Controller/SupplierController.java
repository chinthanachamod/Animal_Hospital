package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.Medicine;
import lk.ijse.Model.MedicineSupplier;
import lk.ijse.Model.Supplier;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {
    @FXML
    private TableColumn<?,?> colS_ID;

    @FXML
    private TableColumn <?,?> colName;

    @FXML
    private TableColumn<?,?> colContact;
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbMedId;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtSuppContact;

    @FXML
    private TextField txtSuppId;

    @FXML
    private TextField txtSuppName;
    public void initialize() {
        getMedicineID();
        setCellValueFactory();
        LoadAllSup();
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtSuppId.setText(newSelection.getSuppId());
                txtSuppName.setText(newSelection.getName());
                txtSuppContact.setText(newSelection.getContactNo());

            }
        });
    }

    private void LoadAllSup() {
        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            ObservableList<Supplier> obList = FXCollections.observableArrayList(supplierList);
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException("Error loading Supplier: " + e.getMessage(), e);
        }

    }

    private void setCellValueFactory() {
        colS_ID.setCellValueFactory(new PropertyValueFactory<>("suppId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ObservableList<Supplier> selectedLocations = tblSupplier.getSelectionModel().getSelectedItems();
        if (selectedLocations.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select Supplier(s) to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Supplier(s)?");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.OK) {
            try {
                for (Supplier supplier : selectedLocations) {
                    boolean isDeleted = SupplierRepo.delete(supplier.getSuppId());
                    if (isDeleted) {
                        tblSupplier.getItems().remove(supplier);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete location: " + supplier.getSuppId()).show();
                    }
                }
                new Alert(Alert.AlertType.CONFIRMATION, "supplier(s) deleted successfully!").show();
                LoadAllSup();
                clearFields();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting supplier(s): " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String S_ID = txtSuppId.getText();
        String M_ID= cmbMedId.getValue();
        String Name = txtSuppName.getText();
        String Contact = txtSuppContact.getText();

        Supplier supplier = new Supplier(S_ID,Name,Contact);
        MedicineSupplier medicineSupplier = new MedicineSupplier(S_ID,M_ID);
        try {
            boolean isSupplierSaved = SupplierRepo.save(supplier);
            if (isSupplierSaved) {
                tblSupplier.getItems().add(supplier);
                boolean isSupMedicineSaved = SupplierRepo.save2(medicineSupplier);
                if (isSupMedicineSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved successfully!").show();
                    LoadAllSup();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Supplier!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Supplier!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving data: " + e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSuppContact.clear();
        txtSuppName.clear();
        txtSuppId.clear();
        cmbMedId.getSelectionModel().clearSelection();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String S_ID = txtSuppId.getText();
        String Name = txtSuppName.getText();
        String Contact = txtSuppContact.getText();

        Supplier supplier = new Supplier(S_ID,Name,Contact);

        try {
            boolean isSupplierUpdate = SupplierRepo.update(supplier);
            if (isSupplierUpdate) {
                int selectedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
                tblSupplier.getItems().set(selectedIndex, supplier);

                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated successfully!").show();
                LoadAllSup();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Supplier!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Supplier: " + e.getMessage()).show();
        }
    }

    @FXML
    void cmbMedIdOnAction(ActionEvent event) {
        String id = String.valueOf(cmbMedId.getValue());
        try {
            Medicine medicine = MedicineRepo.searchById(id);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getMedicineID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = MedicineRepo.getIds();
            obList.addAll(idList);
            cmbMedId.setItems(obList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while fetching Medicine IDs: " + e.getMessage());
        }
    }
    @FXML
    void onSupplierBackClick(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Stage stage = (Stage) MainAnchorpane.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
