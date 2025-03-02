import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Campus_Map_Controller {
     
    private Stage stage;
    private Scene scene;

     @FXML
    void switchToOptionPage(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("BuildingOptionPage.fxml"));
     stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
     scene=new Scene(root);
     stage.setScene(scene);
     stage.show();  
    }

    @FXML
    void switchToWazedBuilding(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("wazed_floor_design.fxml"));
        stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToNewAcademicBuilding(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("10_Storied_building_floor.fxml"));
        stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
