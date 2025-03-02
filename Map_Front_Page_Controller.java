import java.io.IOException;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Map_Front_Page_Controller {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button b_db;

    @FXML
    void AdminLogin(ActionEvent event) throws IOException {

        //Show a dialog box for password.
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Log In");
        //dialog.setHeaderText("Enter password");
        // Font fn = Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 14);
        // dialog.setFont(fn);
        //dialog.headerTextProperty().setFont(Font.font("CALIBRI",22));
        //dialog.setGraphic(new Circle(15, Color.GREEN)); // Custom graphic
        //dialog.contentTextProperty().se
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        

        PasswordField pwd = new PasswordField();
        pwd.setPromptText("Password");
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);

        Label psd = new Label();
        psd.setText("Enter Password");
        psd.setFont(Font.font("CALIBRI",16));

        content.getChildren().addAll(psd, pwd);
        dialog.getDialogPane().setContent(content);
        // dialog.setResultConverter(dialogButton -> {
        //     if (dialogButton == ButtonType.OK) {
        //         return pwd.getText(); //dialog box returns the password
        //     }
        //     return pwd.getText();
        // });

        // Stage stage = (Stage)b_db.getScene().getWindow();
        // Parent root;

        // Optional<String> result = dialog.showAndWait();
        // String password = result.get(); //get the password from dialog box
    
        // if (result.isPresent() && authenticate(password)) {
            
        //     JOptionPane.showMessageDialog(null, "Login Successful.");
        //     root = FXMLLoader.load(getClass().getResource("HstuTableHandleInterface.fxml"));
            
        // }
        // else if(result.get() == null) {
        //     dialog.close();
        //     return;
        // }
        // else {
        //      JOptionPane.showMessageDialog(null, "Wrong Password.");
        //     // root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
        //     return;

        // }
        
        // stage.setScene(new Scene(root));

        Stage stage = (Stage)b_db.getScene().getWindow();
        Parent root;

        Optional<ButtonType> result = dialog.showAndWait(); 
        String password = pwd.getText();
        if (result.get() == ButtonType.OK && result.isPresent() && authenticate(password)) {
            
            JOptionPane.showMessageDialog(null, "Login Successful.");
            root = FXMLLoader.load(getClass().getResource("HstuTableHandleInterface.fxml"));
                        
        }
                    
        else if(result.get() == ButtonType.OK && result.isPresent() && !authenticate(password)) {
            JOptionPane.showMessageDialog(null, "Wrong Password.");
            // root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
            return;
        }
            //}
        else if(result.get() == ButtonType.CANCEL){
            dialog.close();
            return;
        }
        else return;
        
        stage.setScene(new Scene(root));
             
    }

    private boolean authenticate( String password) {
        // Replace with your authentication logic (e.g., database check)
        return  password.equals("1234");
    }

    @FXML
    void DeanLogin(ActionEvent event) {
       
         //Stage stage = (Stage)b_db.getScene().getWindow();
         //stage.close();
         new LoginPage().loginPage();
        // new AllocatingPage();
    }
    @FXML
    void switchToOptionPage(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("BuildingOptionPage.fxml"));
     stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
     scene=new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

}
