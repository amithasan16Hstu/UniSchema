import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AddRoomTypeController implements Initializable{

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<room_types, String> col_roomTypes;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<room_types> table_Types;

    @FXML
    private TextField txt_field;

    @FXML
    private Button updateButton;

    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ObservableList<room_types> listM;
    ObservableList<room_types> dataList;

    @FXML
    void Add(ActionEvent event) {

        if(!txt_field.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            String sql = "insert into room_types(RoomTypes) values(?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_field.getText());
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Successfully added to database.");
                UpdateTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            txt_field.clear();
        }
        else{
            JOptionPane.showMessageDialog(null, "Missing 'Room Type'.");
        }
        
    }

    @FXML
    void Delete(ActionEvent event) {

        if(!txt_field.getText().isEmpty()){
            //confirm to delete
            String selection = "";
            Alert alert = new Alert(AlertType.CONFIRMATION, selection, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText("Are you sure you want to delete the row?");
            alert.showAndWait();
    
            if (alert.getResult() == ButtonType.YES) {
                conn = mysqlconnect.ConnectDb();
                String sql1 = "delete from room_types where RoomTypes = ?";
                try {
                    pst = conn.prepareStatement(sql1);
                    pst.setString(1, txt_field.getText());
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Successfully deleted from database.");
                    UpdateTable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                txt_field.clear();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "'Room-type' not found.");
        }
    }

    String oldValue = null;
    
    @FXML
    void Update(ActionEvent event) {

        if(!txt_field.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            try{
                String value1 = txt_field.getText();
                
                String sql = "update room_types set RoomTypes= ? where RoomTypes=? ";
                
                pst= conn.prepareStatement(sql);
                pst.setString(1, value1);
                pst.setString(2, oldValue);
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Successfully updated in database.");
                UpdateTable();
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            txt_field.clear();
        }
        else{
            JOptionPane.showMessageDialog(null, "'Room Type' not found.");
        }
    }

    @FXML
    void getSelected(MouseEvent event) {

        index = table_Types.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_field.setText(col_roomTypes.getCellData(index).toString());
        oldValue = txt_field.getText();
    }

    /// to conduct set table in RoomTypes table
    public static ObservableList<room_types> setRoomTypesTable(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<room_types> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT RoomTypes FROM room_types;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new room_types(rs.getString("RoomTypes")));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void UpdateTable(){
        col_roomTypes.setCellValueFactory(new PropertyValueFactory<room_types,String>("RoomTypes"));
        
        listM = setRoomTypesTable();
        table_Types.setItems(listM);
        table_Types.setStyle("-fx-font-size: 16;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
    }

}
