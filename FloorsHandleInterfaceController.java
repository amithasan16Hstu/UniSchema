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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FloorsHandleInterfaceController implements Initializable {

    @FXML
    private Button RoomsButton;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> buildingName_choiceBox;

    @FXML
    private TableColumn<building_floors, String> buildingName_col;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<building_floors, String> floorID_col;

    @FXML
    private TextField floorID_txt;

    @FXML
    private TableColumn<building_floors, String> floorNumber_col;

    @FXML
    private TextField floorNumber_txt;

    @FXML
    private Button floorsButton;

    @FXML
    private Button purposedFloorButton;

    @FXML
    private Button structuralEntitiesButton;

    @FXML
    private TableColumn<building_floors, Integer> totalRooms_col;

    @FXML
    private TextField totalRooms_txt;

    @FXML
    private Button updateButton;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<building_floors> table_BuildingFloors;

    @FXML
    private Button refreshButton;

    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    ObservableList<building_floors> listM;
    ObservableList<building_floors> dataList;

    @FXML
    void Add(ActionEvent event) {

        if(!floorID_txt.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            //get the building ID for the input building name
            String name =null;
            String sql1 = "select ID from structural_entities where Name = ?";
            try{
                PreparedStatement pst1 = conn.prepareStatement(sql1); 
                pst1.setString(1, buildingName_choiceBox.getValue());
                ResultSet rs =pst1.executeQuery();
                while(rs.next()){
                    name = rs.getString("ID");
                }
            }catch(Exception e){

            }
            //insert data in proper tables of mysql database
            String sql = "insert into building_floors(floor_id,ID,floor_number,total_rooms) values(?,?,?,?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, floorID_txt.getText());
                pst.setString(2, name);
                pst.setString(3, floorNumber_txt.getText());
                pst.setString(4, totalRooms_txt.getText());
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

        index = table_BuildingFloors.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        oldFID = floorID_col.getCellData(index).toString(); //used to update the row while id needs to be changed

        floorID_txt.setText(floorID_col.getCellData(index).toString());
        buildingName_choiceBox.setValue(buildingName_col.getCellData(index).toString());
        floorNumber_txt.setText(floorNumber_col.getCellData(index).toString());
        totalRooms_txt.setText(totalRooms_col.getCellData(index).toString());
    }

    @FXML
    void Update(ActionEvent event) {

        if(!floorID_txt.getText().isEmpty()){
            try {
                conn = mysqlconnect.ConnectDb();
                String value1 = floorID_txt.getText();

                //get the building ID for the input building name
                String value2 =null;
                String sql1 = "select ID from structural_entities where Name = ?";
                try{
                    PreparedStatement pst1 = conn.prepareStatement(sql1); 
                    pst1.setString(1, buildingName_choiceBox.getValue());
                    ResultSet rs =pst1.executeQuery();
                    while(rs.next()){
                        value2 = rs.getString("ID");
                    }
                }catch(Exception e){

                }

                String value3 = floorNumber_txt.getText();
                String value4 = totalRooms_txt.getText();
                String sql = "update building_floors set floor_id= ? ,ID= ? ,floor_number= ? ,total_rooms= ? where floor_id=? ";
                pst= conn.prepareStatement(sql);
                pst.setString(1, value1);
                pst.setString(2, value2);
                pst.setString(3, value3);
                pst.setString(4, value4);
                pst.setString(5, oldFID);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully updated in database.");
                UpdateTable();
                Search();
                //Refresh(event);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else{
        JOptionPane.showMessageDialog(null, "'Floor ID' not found.");
        }
    }

    @FXML
    void Delete(ActionEvent event) {

        if(!floorID_txt.getText().isEmpty()){
        //confirming to delete
        String selection = "Caution: Related contents of other tables will also be deleted.";
        Alert alert = new Alert(AlertType.CONFIRMATION, selection, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setHeaderText("Are you sure you want to delete the row with 'Floor ID = "+ floorID_txt.getText()+"?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //deleting the row
            conn = mysqlconnect.ConnectDb();
            String sql1 = "delete from building_floors where floor_id = ?";
            try {
                pst = conn.prepareStatement(sql1);
                pst.setString(1, floorID_txt.getText());
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
            JOptionPane.showMessageDialog(null, "'Floor ID' not found.");
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

    /// to conduct search and set table in building_floors table
    public static ObservableList<building_floors> getData_building_floors(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<building_floors> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT floor_id,Name as building_name,floor_number,total_rooms FROM structural_entities join building_floors using(ID) ORDER BY LPAD(lower(floor_id), 15,0) asc;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new building_floors(rs.getString("floor_id"),rs.getString("building_name"),rs.getString("floor_number"), rs.getInt("total_rooms")));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    @FXML
    void Search() {

        floorID_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("floor_id"));
        buildingName_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("building_name"));
        floorNumber_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("floor_number"));
        totalRooms_col.setCellValueFactory(new PropertyValueFactory<building_floors,Integer>("total_rooms"));

        dataList = getData_building_floors();
        table_BuildingFloors.setItems(dataList);
        FilteredList<building_floors> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(building_floors -> {
                if(newValue==null || newValue.isEmpty())
                return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(building_floors.getFloor_id().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(building_floors.getBuilding_name().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(building_floors.getFloor_number().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(String.valueOf(building_floors.getTotal_rooms()). indexOf(lowerCaseFilter) !=-1)
                return true;
                
                else 
                return false;
            });
        });
        SortedList<building_floors> sortedDate = new SortedList<>(filteredData);
        sortedDate.comparatorProperty().bind(table_BuildingFloors.comparatorProperty());
        table_BuildingFloors.setItems(sortedDate);
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

        floorID_txt.clear();
        buildingName_choiceBox.getSelectionModel().clearSelection();
        buildingName_choiceBox.getItems().clear();
        floorNumber_txt.clear();
        totalRooms_txt.clear();
        filterField.clear();

        initialize(null, null);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        floorsButton.setStyle("-fx-border-color: #0000ff; ");
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
        floorID_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("floor_id"));
        buildingName_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("building_name"));
        floorNumber_col.setCellValueFactory(new PropertyValueFactory<building_floors,String>("floor_number"));
        totalRooms_col.setCellValueFactory(new PropertyValueFactory<building_floors,Integer>("total_rooms"));
        
        
        
        listM = getData_building_floors();
        System.out.println(listM);
        table_BuildingFloors.setItems(listM);
        table_BuildingFloors.setStyle("-fx-font-size: 16;");
    }

}
