
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

import javax.swing.JOptionPane;

public class Building_Option_Page_Controller {

    private Stage stage;
    private Scene scene;

    @FXML
    void switch_to_MapFrontPage(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
     stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
     scene=new Scene(root);
     stage.setScene(scene);
     stage.show();
    }


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        // Add options to the ComboBox
        comboBox.getItems().addAll("HSTU Campus Map","Dr.M.A.Wazed Building","Dr.Muhammad Qudrat-I- Khuda Academic Building","Academic Building-1","Academic Building-2","Academic Building-3","Sheikh Russel Hall",
	                                " Shaheed President Ziaur Rahman Hall","Tajuddin Ahmad Hall"," International Hall","Bangabandhu Sheikh Mujibur Rahman Hall"," Bangamata Sheikh Fazilatunnesa Mujib Hall",
							"Ivy Rahman Hall"," Kobi Sufia Kamal Hall"," Sheikh Sayera Khatun Hall","Auditorium-1","Auditorium-2","Library Building","Canteen,Bank & TSC","Medical Center","Field Lab",
							   "VIP Guest House & IRT","Gymnesium","Administrative Building","Central Mosque","Veterinary Hospital","Farm","HSTU School","Mosque","Staff Residence","Guest House","Residence of VC");
        // Set a default value (optional)
        comboBox.setPromptText("Select an Option");
        comboBox.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 25px; -fx-font-weight: bold;");
        
    }

    @FXML
    public void handleSelection(ActionEvent event) throws IOException {
        // Get the selected option
        String selectedOption = comboBox.getValue();

        // Switch to another controller based on the selection
        Stage stage = (Stage) comboBox.getScene().getWindow();
        Parent root;

        if ("HSTU Campus Map".equals(selectedOption)) {
            root = FXMLLoader.load(getClass().getResource("Campus_Map.fxml"));
        } else if ("Dr.M.A.Wazed Building".equals(selectedOption)) {
            root = FXMLLoader.load(getClass().getResource("wazed_floor_design.fxml"));
        } else if ("Dr.Muhammad Qudrat-I- Khuda Academic Building".equals(selectedOption)) {
            root = FXMLLoader.load(getClass().getResource("Ten_Storied_building_floor.fxml"));
        } else {
            // Handle unexpected cases (optional)
            // UIManager.put("OptionPane.messageFont", new Font("Arial", 14));
            JOptionPane.showMessageDialog(null, "No matching view for the selected option.");
            return;
        }

        // Set the new scene
        stage.setScene(new Scene(root));
    }
}
