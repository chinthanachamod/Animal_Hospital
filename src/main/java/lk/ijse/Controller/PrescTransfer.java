package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.Model.CartTm;
import lk.ijse.Model.Medicine;
import lk.ijse.Model.Prescription;
import lk.ijse.Model.petMediDetail;
import lk.ijse.repository.MedicineRepo;
import lk.ijse.repository.PrescMediRepo;
import lk.ijse.repository.PrescriptionRepo;
import lk.ijse.util.Regex;
import lk.ijse.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescTransfer {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbMedicine;

    @FXML
    private ComboBox<String> cmbPresc;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colM_ID;

    @FXML
    private TableColumn<?, ?> colP_ID;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private Label lblMedicine;


    @FXML
    private Label lblMedicineQtyOnHand;

    @FXML
    private Label lblPrescription;

    @FXML
    private TableView<CartTm> tblPM;

    @FXML
    private JFXTextField txtQty;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        getPrescID();
        getMedicineID();
        setCellValueFactory();
        LoadAllPresc();
    }

    private void LoadAllPresc() {
        ObservableList<petMediDetail> obList = FXCollections.observableArrayList();
        try {
            List<petMediDetail> ps = PrescMediRepo.getAll();
            for (petMediDetail psList : ps) {
                petMediDetail tm = new petMediDetail(
                        psList.getMedicine_ID(),
                        psList.getP_ID(),
                        psList.getQTY()
                );
                obList.add(tm);
            }
            // tblPM.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colP_ID.setCellValueFactory(new PropertyValueFactory<>("P_IDC"));
        colM_ID.setCellValueFactory(new PropertyValueFactory<>("Medicine_IDC"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("QTYC"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemoveC"));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String PrescID = cmbPresc.getValue();
        String MedicineID = cmbMedicine.getValue();
        int Qty = Integer.parseInt(txtQty.getText());

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                CartTm selectedIndex =  tblPM.getSelectionModel().getSelectedItem();
                obList.remove(selectedIndex);
                tblPM.refresh();
            }
        });

        CartTm tm = new CartTm( MedicineID,PrescID, Qty, btnRemove);
        obList.add(tm);

        tblPM.setItems(obList);
        txtQty.setText("");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
    }

    private void clearFields() {
        cmbPresc.getSelectionModel().clearSelection();
        cmbMedicine.getSelectionModel().clearSelection();
        lblMedicine.setText("");
        lblMedicineQtyOnHand.setText("");
        lblPrescription.setText("");
        txtQty.clear();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        List<CartTm> toRemove = new ArrayList<>();
        for (CartTm tm : obList) {
            petMediDetail od = new petMediDetail(
                    tm.getMedicine_IDC(),
                    tm.getP_IDC(),
                    tm.getQTYC()
            );
            try {
                boolean isSaved = PrescMediRepo.save(od);
                if (isSaved) {
                    boolean isUpdate = MedicineRepo.updateQty(tm.getMedicine_IDC(), tm.getQTYC());
                    if (isUpdate) {
                        toRemove.add(tm);
                        new Alert(Alert.AlertType.CONFIRMATION, "Place successfully!").show();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Place!").show();
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while Place: " + e.getMessage()).show();
            }
        }
        obList.removeAll(toRemove);
        tblPM.getItems().clear();
    }

    @FXML
    void cmbMedicineOnAction(ActionEvent event) {
        String id = String.valueOf(cmbMedicine.getValue());
        try {
            Medicine medicine = MedicineRepo.searchById(id);
            if (medicine != null) {
                lblMedicine.setText(medicine.getDescription());
                lblMedicineQtyOnHand.setText(String.valueOf(medicine.getQty()));
            } else {
                // Handle case when medicine is not found
                lblMedicine.setText("Medicine not found");
                lblMedicineQtyOnHand.setText("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getMedicineID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = MedicineRepo.getIds();
            obList.addAll(idList);
            cmbMedicine.setItems(obList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while fetching Medicine IDs: " + e.getMessage());
        }
    }

    private void getPrescID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = PrescriptionRepo.getIds();
            obList.addAll(idList);
            cmbPresc.setItems(obList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while fetching Prescription IDs: " + e.getMessage());
        }
    }
    @FXML
    void cmbPrescOnAction(ActionEvent event) {
        String id = String.valueOf(cmbPresc.getValue());
        try {
            Prescription prescription = PrescriptionRepo.searchById(id);
            if (prescription != null) {
                lblPrescription.setText(prescription.getDiagnosis());
            } else {
                // Handle case when prescription is not found
                lblPrescription.setText("Prescription not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnMedDetailesOnActionClick(ActionEvent event) {

    }
    public void onQty(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY, txtQty);
    }

}