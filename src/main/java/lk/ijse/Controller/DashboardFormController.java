package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();

        String text;

        if(hour >= 6 && hour < 12){
            text = "Good morning";
        }  else if(hour >= 12 && hour < 18){
            text = "Good afternoon";
        } else {
            text = "Good evening";
        }

        //

    }


    public void onPetActionClick(ActionEvent actionEvent) {
        createStageScene("/View/Pet.fxml");
    }

    public void onHomeActionClick(ActionEvent actionEvent) {
        createStageScene("/View/DashboardForm.fxml.fxml");
    }

    public void onPetOwnerActionClick(ActionEvent actionEvent) {
        createStageScene("/View/PetOwner.fxml");
    }

    public void onAppointmentActionClick(ActionEvent actionEvent) {
        createStageScene("/View/Appointment.fxml");
    }

    public void onPrescriptionActionClick(ActionEvent actionEvent) {
        createStageScene("/View/Prescription.fxml");
    }

    public void onMedicineActionClick(ActionEvent actionEvent) {
        createStageScene("/View/Medicine.fxml");
    }

    public void onSupplierActionClick(ActionEvent actionEvent) {
        createStageScene("/View/Supplier.fxml");
    }


    private void createStageScene(String path){
        try {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource(path));
            Scene scene = new Scene(rootNode);

            Stage stage = (Stage)this.rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
