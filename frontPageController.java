import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class frontPageController {

    @FXML
    void getStarted(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
     Stage stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
     Scene scene=new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

}
