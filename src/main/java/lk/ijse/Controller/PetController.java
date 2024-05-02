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
import lk.ijse.Model.Pet;
import lk.ijse.Model.PetOwner;
import lk.ijse.repository.PetOwnerRepo;
import lk.ijse.repository.petRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PetController {

    public AnchorPane MainAnchorpane;
    @FXML
    private TextField txtPetId;

    @FXML
    private TextField txtBreed;

    @FXML
    private TextField txtWeight;

    @FXML
    private TextField txtAge;

    @FXML
    private ComboBox<String> cmbPetOwnerId;

    @FXML
    private TableView<Pet> tblPet;

    @FXML
    private TableColumn<Pet, String> colPetID;

    @FXML
    private TableColumn<Pet, String> colPetBreed;

    @FXML
    private TableColumn<Pet, Integer> colPetAge;

    @FXML
    private TableColumn<Pet, String> colWeight;

    @FXML
    private TableColumn<Pet, String> colPetOwnerID;


    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnBack;

    public void initialize() {
        getPetIds();
        setCellValueFactory();
        loadAllPet();

        tblPet.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbPetOwnerId.setValue(newSelection.getPetOwId());
                txtPetId.setText(newSelection.getPetId());
                txtAge.setText(String.valueOf(newSelection.getAge()));
                txtWeight.setText(newSelection.getWeight());
                txtBreed.setText(newSelection.getBreed());
            }
        });
    }

    private void setCellValueFactory() {
        colPetID.setCellValueFactory(new PropertyValueFactory<>("petId"));
        colPetAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colPetBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        colPetOwnerID.setCellValueFactory(new PropertyValueFactory<>("petOwId"));
    }

    private void loadAllPet() {
        try {
            List<Pet> petList = petRepo.PetRepo.getAll();
            ObservableList<Pet> obList = FXCollections.observableArrayList(petList);
            tblPet.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException("Error loading Pet: " + e.getMessage(), e);
        }
    }

    @FXML
    private void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        ObservableList<Pet> selectedPet = tblPet.getSelectionModel().getSelectedItems();
        if (selectedPet.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select Pet(s) to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Pet(s)?");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.OK) {
            try {
                for (Pet pet : selectedPet) {
                    boolean isDeleted = petRepo.PetRepo.delete(pet.getPetId());
                    if (isDeleted) {
                        tblPet.getItems().remove(pet);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete pet: " + pet.getPetId()).show();
                    }
                }
                new Alert(Alert.AlertType.CONFIRMATION, "pet(s) deleted successfully!").show();
                clearFields();
                loadAllPet();
                clearFields();


            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting pet(s): " + e.getMessage()).show();
            }
        }
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) throws SQLException {
        String PetOwnerID = cmbPetOwnerId.getValue();
        String petID = txtPetId.getText();
        String Breed = txtBreed.getText();
        String Weight = txtWeight.getText();
        int Age = Integer.parseInt(txtAge.getText());

        Pet pet = new Pet(petID,Age,Weight,Breed,PetOwnerID);
        try {
            boolean isSaved = petRepo.PetRepo.save(pet);
            if (isSaved) {
                tblPet.getItems().add(pet);
                new Alert(Alert.AlertType.CONFIRMATION, "pet saved successfully!").show();
                loadAllPet();
                clearFields();

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save pet!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving pet: " + e.getMessage()).show();
        }


    }



    @FXML
    private void onPetBackClick(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/View/DashboardForm.fxml"));
        Stage stage = (Stage) MainAnchorpane.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
    }

    @FXML
    private void btnUpdateOnActon(ActionEvent event) {
        String PetOwnerID = cmbPetOwnerId.getValue();
        String petID = txtPetId.getText();
        String Breed = txtBreed.getText();
        String Weight = txtWeight.getText();
        int Age = Integer.parseInt(txtAge.getText());

        Pet pet = new Pet(petID,Age,Weight,Breed,PetOwnerID);
        try {
            boolean isUpdated = petRepo.PetRepo.update(pet);
            if (isUpdated) {
                int selectedIndex = tblPet.getSelectionModel().getSelectedIndex();
                tblPet.getItems().set(selectedIndex, pet);
                new Alert(Alert.AlertType.CONFIRMATION, "Location Pet successfully!").show();
                loadAllPet();
                clearFields();


            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Pet location!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while Pet location: " + e.getMessage()).show();
        }

    }

    @FXML
    private void cmbPetOwnerIdOnAction(ActionEvent event) {
        String id = cmbPetOwnerId.getValue();
        try {
            PetOwner petOwner = PetOwnerRepo.searchById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void getPetIds() {
        try {
            List<String> idList = PetOwnerRepo.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList(idList);
            cmbPetOwnerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        cmbPetOwnerId.getSelectionModel().clearSelection();
        txtAge.clear();
        txtBreed.clear();
        txtPetId.clear();
        txtWeight.clear();
    }

}
