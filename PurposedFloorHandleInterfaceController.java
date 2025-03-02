import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PurposedFloorHandleInterfaceController implements Initializable {

    @FXML
    private TableColumn<special_purposed_floor, String> BuildingName_col;

    @FXML
    private TableColumn<special_purposed_floor, String> FloorNumber_col;

    @FXML
    private Button RoomsButton;

    @FXML
    private TableColumn<special_purposed_floor, String> Use_col;

    @FXML
    private Button addButton;

    @FXML
    private ComboBox<String> buildingName_choiceBox;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> floorNumber_choiceBox;

    @FXML
    private Button floorsButton;

    @FXML
    private Button purposedFloorButton;

    @FXML
    private Button structuralEntitiesButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField use_txt;

    @FXML
    private TextField filterField;
    
    @FXML
    private TableView<special_purposed_floor> table_purposedFloors;

    @FXML
    private Button refreshButton;

    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ObservableList<special_purposed_floor> listM;
    ObservableList<special_purposed_floor> dataList;

    @FXML
    void Add(ActionEvent event) {

        if(!(getFloorID()==null)){
            conn = mysqlconnect.ConnectDb();
            //get the building ID for the input building name
            String buildingID =null;
            String sql1 = "select ID from structural_entities where Name = ?";
            try{
                PreparedStatement pst1 = conn.prepareStatement(sql1); 
                pst1.setString(1, buildingName_choiceBox.getValue());
                ResultSet rs =pst1.executeQuery();
                while(rs.next()){
                    buildingID = rs.getString("ID");
                }
            }catch(Exception e){

            }
            //get the floor ID for the input floor number and building name
            String floorID =null;
            String sql2 = "select floor_id from building_floors where (ID,floor_number) = (?,?)";
            try{
                PreparedStatement pst1 = conn.prepareStatement(sql2); 
                pst1.setString(1,buildingID);
                pst1.setString(2, floorNumber_choiceBox.getValue());
                ResultSet rs =pst1.executeQuery();
                while(rs.next()){
                    floorID = rs.getString("floor_id");
                }
            }catch(Exception e){

            }
            //insert data in proper tables of mysql database
            String sql = "insert into special_purposed_floor(floor_id,for_use) values(?,?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, floorID);
                pst.setString(2, use_txt.getText());
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Successfully added to database.");
                UpdateTable();
                Search();
                //Refresh(event);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Missing 'Floor ID'.");
            }
    }

    String oldFID = null;
    @FXML
    void getSelected(MouseEvent event) {

        index = table_purposedFloors.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        oldFID = getFloorID();
        buildingName_choiceBox.setValue(BuildingName_col.getCellData(index).toString());
        floorNumber_choiceBox.setValue(FloorNumber_col.getCellData(index).toString());
        use_txt.setText(Use_col.getCellData(index).toString());
    }

    String getFloorID(){

        String value1 =null;
        try {
            conn = mysqlconnect.ConnectDb();

             //get the building ID for the input building name
            String buildingID =null;
            String sql1 = "select ID from structural_entities where Name = ?";
            try{
                PreparedStatement pst1 = conn.prepareStatement(sql1); 
                pst1.setString(1, buildingName_choiceBox.getValue());
                ResultSet rs =pst1.executeQuery();
                while(rs.next()){
                    buildingID = rs.getString("ID");
                }
            }catch(Exception e){

            }

            //get the floor ID for the input floor number and building name
        
            String sql2 = "select floor_id from building_floors where (ID,floor_number) = (?,?)";
            try{
            PreparedStatement pst1 = conn.prepareStatement(sql2); 
            pst1.setString(1,buildingID);
            pst1.setString(2, floorNumber_choiceBox.getValue());
            ResultSet rs =pst1.executeQuery();
            while(rs.next()){
                value1 = rs.getString("floor_id");
            }
            }catch(Exception e){

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return value1;
    }
    @FXML
    void Update(ActionEvent event) {

        if(!(getFloorID()==null)){
            conn = mysqlconnect.ConnectDb();
            try{
                String value1 = getFloorID();
                String value2 = use_txt.getText();
                
                String sql = "update special_purposed_floor set floor_id= ? ,for_use= ? where floor_id=? ";
                pst= conn.prepareStatement(sql);
                pst.setString(1, value1);
                pst.setString(2, value2);
                pst.setString(3, oldFID);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully updated in database.");
                UpdateTable();
                Search();
                //Refresh(event);
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else{
        JOptionPane.showMessageDialog(null, "Floor not found.");
        }
        
    }

    @FXML
    void Delete(ActionEvent event) {

        if(!(getFloorID()==null)){
        //confirm to delete
        String selection = "Are you sure you want to delete the row?";
        Alert alert = new Alert(AlertType.CONFIRMATION, selection, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            conn = mysqlconnect.ConnectDb();
            String sql1 = "delete from special_purposed_floor where floor_id = ?";
            try {
                pst = conn.prepareStatement(sql1);
                pst.setString(1, getFloorID());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully deleted from database.");
                UpdateTable();
                Search();
                //Refresh(event);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
     }
     else{
        JOptionPane.showMessageDialog(null, "Floor not found.");
    }
        
    }

    @FXML
    void HandleStructuralEntities(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("HstuTableHandleInterface.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void HandleFloors(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FloorsHandleInterface.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void HandleRooms(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("RoomsHandleInterface.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void HandlePurposedFloor(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("PurposedFloorHandleInterface.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /// to conduct search and set table in special_purposed_floors table
    public static ObservableList<special_purposed_floor> getData_special_purposed_floors(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<special_purposed_floor> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT Name,floor_number,for_use FROM (structural_entities natural join building_floors) join special_purposed_floor using(floor_id) ORDER BY LPAD(lower(name), 15,0) asc;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new special_purposed_floor(rs.getString("Name"),rs.getString("floor_number"),rs.getString("for_use")));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    @FXML
    void Search() {

        BuildingName_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("building_name"));
        FloorNumber_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("floor_number"));
        Use_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("use"));

        dataList = getData_special_purposed_floors();
        table_purposedFloors.setItems(dataList);
        FilteredList<special_purposed_floor> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(special_purposed_floor -> {
                if(newValue==null || newValue.isEmpty())
                return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(special_purposed_floor.getBuilding_name().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(special_purposed_floor.getFloor_number().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(special_purposed_floor.getUse().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                
                else 
                return false;
            });
        });
        SortedList<special_purposed_floor> sortedDate = new SortedList<>(filteredData);
        sortedDate.comparatorProperty().bind(table_purposedFloors.comparatorProperty());
        table_purposedFloors.setItems(sortedDate);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
        Stage stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Refresh(ActionEvent event) {

        buildingName_choiceBox.getSelectionModel().clearSelection();
        buildingName_choiceBox.getItems().clear();
        floorNumber_choiceBox.getSelectionModel().clearSelection();
        floorNumber_choiceBox.getItems().clear();
        use_txt.clear();
        filterField.clear();

        initialize(null, null);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       purposedFloorButton.setStyle("-fx-border-color: #0000ff; ");
       UpdateTable();
       Search();
       //load items to buildingName_choiceBox
        conn = mysqlconnect.ConnectDb();
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "select distinct Name from structural_entities where Type = 'Building';";
        try{

            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(rs.getString("Name"));
            }

        }catch(Exception e){

        }
        buildingName_choiceBox.setItems(list);

        //set icons
        Image imageDecline1 = new Image(getClass().getResourceAsStream("icons/icons8-refresh-25.png"));
        refreshButton.setGraphic(new ImageView(imageDecline1));

        Image imageDecline2 = new Image(getClass().getResourceAsStream("icons/icons8-back-25.png"));
        backButton.setGraphic(new ImageView(imageDecline2));

        filterField.setStyle("-fx-background-image:url('icons/icons8-search-25.png'); -fx-background-repeat: no-repeat; -fx-background-position: right center;");

    }

    public void UpdateTable(){
        BuildingName_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("building_name"));
        FloorNumber_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("floor_number"));
        Use_col.setCellValueFactory(new PropertyValueFactory<special_purposed_floor,String>("use"));
        
        
        listM = getData_special_purposed_floors();
        table_purposedFloors.setItems(listM);
        table_purposedFloors.setStyle("-fx-font-size: 16;");
    }

    @FXML
    void setFloorNumber(ActionEvent event) {
        //floorNumber_choiceBox.visibleProperty().bind(Bindings.isNotNull(buildingName_choiceBox.valueProperty()));
        Connection conn = mysqlconnect.ConnectDb();
        //get the building ID for the input building name
        String buildingID = null ;
        String sql1 = "select ID from structural_entities where Name = ?";
        try{
            PreparedStatement pst1 = conn.prepareStatement(sql1); 
            pst1.setString(1, buildingName_choiceBox.getValue());
            ResultSet rs =pst1.executeQuery();
            while(rs.next()){
                buildingID = rs.getString("ID");
            }
        }catch(Exception e){

        }
        //set items to floorNumbers of buildingID
        ObservableList<String> list1 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT floor_number FROM building_floors where ID = ?;");
            ps.setString(1,buildingID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list1.add(rs.getString("floor_number"));
                
            }
        } catch (Exception e) {
        }
        floorNumber_choiceBox.setItems(list1);
    }
    

}
