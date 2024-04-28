package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane rootPane;

    public void onPetActionClick(ActionEvent actionEvent) {
        try {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/View/Pet.fxml"));
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
