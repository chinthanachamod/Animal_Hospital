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
import lk.ijse.Model.Appointment;
import lk.ijse.Model.PetOwner;
import lk.ijse.Model.Veterinarian;
import lk.ijse.repository.AppoinmentRepo;
import lk.ijse.repository.PetOwnerRepo;
import lk.ijse.repository.VeterinaringRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AppointmentController {

    @FXML
    private TextField txtTime;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbPetOwner;

    @FXML
    private ComboBox<String> cmbVeternarian;

    @FXML
    private TableColumn<?, ?> colAppointmentID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPetOwnerID;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colVeternarianID;

    @FXML
    private TableView<Appointment> tblAppointment;

    @FXML
    private TextField txtAppointment;

    public void initialize() {
        setCellValueFactory();
        getPetOwnerIds();
        getVeternarianIds();
        loadAllBooking();
    }

    private void setCellValueFactory() {
        colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPetOwnerID.setCellValueFactory(new PropertyValueFactory<>("petOwId"));
        colVeternarianID.setCellValueFactory(new PropertyValueFactory<>("vetId"));
    }

    private void loadAllBooking() {
        ObservableList<Appointment> obList = FXCollections.observableArrayList();

        try {
            List<Appointment> AppointmentList = AppoinmentRepo.getAll();
            obList.addAll(AppointmentList);
            tblAppointment.setItems(obList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading appointments: " + e.getMessage());
        }
    }

    @FXML
    void DatePickerOnAction(ActionEvent event) {
        LocalDate selectedDate = DatePicker.getValue();
        if (selectedDate != null) {
            System.out.println("Selected date: " + selectedDate);
        } else {
            System.out.println("No date selected");
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String appId = txtAppointment.getText();
        String time = txtTime.getText();
        String petOwId = cmbPetOwner.getValue();
        String vetId = cmbVeternarian.getValue();

        LocalDate selectedDate = DatePicker.getValue();
        if (selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Please select a booking date.");
            return;
        }

        Date bookingDate = Date.valueOf(selectedDate);

        Appointment appointment = new Appointment(appId, bookingDate, time, petOwId, vetId);

        try {
            boolean isAppointmentSaved = AppoinmentRepo.save(appointment);
            if (isAppointmentSaved) {
                showAlert(Alert.AlertType.CONFIRMATION, "Booking placed successfully!");
                loadAllBooking();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save Booking!");
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Error saving appointment: " + ex.getMessage());
        }
    }

    @FXML
    void cmbPetOwnerOnAction(ActionEvent event) {
        String id = cmbPetOwner.getValue();
        try {
            PetOwnerRepo petOwner = PetOwnerRepo.searchById(id);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while searching for showroom: " + e.getMessage());
        }    }

    @FXML
    void cmbVeternarianOnAction(ActionEvent event) {

    }

    private void getPetOwnerIds() {
        try {
            List<String> idList = PetOwnerRepo.getIds();
            cmbPetOwner.setItems(FXCollections.observableArrayList(idList));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error fetching Pet Owner IDs: " + e.getMessage());
        }
    }

    private void getVeternarianIds() {
        try {
            List<String> idList = VeterinaringRepo.getIds();
            cmbVeternarian.setItems(FXCollections.observableArrayList(idList));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error fetching Veterinarian IDs: " + e.getMessage());
        }
    }

    @FXML
    void onAppointmentBackClick(ActionEvent event) throws IOException {
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
