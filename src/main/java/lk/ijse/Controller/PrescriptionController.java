package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Model.Prescription;
import lk.ijse.repository.PrescriptionRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PrescriptionController {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPrescBack;


    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbPresVetId;

    @FXML
    private TextField txtPrescDiagnos;

    @FXML
    private TextField txtPrescId;

    public void initialize() throws SQLException {
        setVetIDs();
    }

    public void setVetIDs() throws SQLException {
        List<String> ids =  PrescriptionRepo.getVetIDs();

        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : ids){
            obList.add(un);
        }
        cmbPresVetId.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        //TODO > Step 1 - Create save action
         String prescId = txtPrescId.getText();
         String diagnosis= txtPrescDiagnos.getText();
         String vetId = String.valueOf(cmbPresVetId.getValue());

        Prescription prescription = new Prescription(prescId, diagnosis, vetId);

        try {
            boolean isSaved = PrescriptionRepo.save(prescription);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription saved!").show();
                //clearFields();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbPresVetIdOnAction(ActionEvent event) {

    }

    @FXML
    void onPrescriptionBackClick(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Stage stage = (Stage) MainAnchorpane.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
    }

}
