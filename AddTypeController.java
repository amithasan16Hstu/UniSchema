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

public class AddTypeController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<type, String> col_types;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<type> table_Types;

    @FXML
    private TextField txt_field;

    @FXML
    private Button updateButton;

    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ObservableList<type> listM;
    ObservableList<type> dataList;

    @FXML
    void Add(ActionEvent event) {

        if(!txt_field.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            String sql = "insert into type(types) values(?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_field.getText());
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Add succes");
                UpdateTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            txt_field.clear();
        }
        else{
            JOptionPane.showMessageDialog(null, "Missing 'Type'.");
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
                String sql1 = "delete from type where types = ?";
                try {
                    pst = conn.prepareStatement(sql1);
                    pst.setString(1, txt_field.getText());
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Delete");
                    UpdateTable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                txt_field.clear();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "'Type' not found.");
        }
}

    String oldValue = null;
    @FXML
    void Update(ActionEvent event) {

        if(!txt_field.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            try{
                String value1 = txt_field.getText();
                
                String sql = "update type set types= ? where types= ? ";
                pst= conn.prepareStatement(sql);
                pst.setString(1, value1);
                pst.setString(2, oldValue);
                pst.execute();
                //JOptionPane.showMessageDialog(null, "Update");
                UpdateTable();
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            txt_field.clear();
        }
        else{
            JOptionPane.showMessageDialog(null, "'Type' not found.");
        }
    }

    @FXML
    void getSelected(MouseEvent event) {

        index = table_Types.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_field.setText(col_types.getCellData(index).toString());
        oldValue = txt_field.getText();
    }

    /// to conduct set table in Type table
    public static ObservableList<type> setTypeTable(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<type> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT types FROM type;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new type(rs.getString("types")));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void UpdateTable(){
        col_types.setCellValueFactory(new PropertyValueFactory<type,String>("types"));
        
        listM = setTypeTable();
        table_Types.setItems(listM);
        table_Types.setStyle("-fx-font-size: 16;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
    }

}
