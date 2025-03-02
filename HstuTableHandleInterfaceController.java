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

public class HstuTableHandleInterfaceController implements Initializable {

    @FXML
    private ChoiceBox<String> Category_choiceBox;

    @FXML
    private TableColumn<structural_entities, String> Category_column;

    @FXML
    private TableColumn<structural_entities, String> ID_column;

    @FXML
    private TextField ID_txt;

    @FXML
    private ChoiceBox<String> Location_choiceBox;

    @FXML
    private TableColumn<structural_entities, String> Location_column;

    @FXML
    private TableColumn<structural_entities, String> Name_column;

    @FXML
    private TextField Name_txt;

    @FXML
    private Button RoomsButton;

    @FXML
    private ChoiceBox<String> Type_ChoiceBox;

    @FXML
    private TableColumn<structural_entities, String> Type_column;

    @FXML
    private Button addButton;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button addTypeButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button floorsButton;

    @FXML
    private Button purposedFloorButton;

    @FXML
    private Button structuralEntitiesButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<structural_entities> table_structural_entities;

    @FXML
    private Button refreshButton;

    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    String oldID = null;
    @FXML
    void getSelected(MouseEvent event) {

        index = table_structural_entities.getSelectionModel().getSelectedIndex();
    if (index <= -1){
        return;
    }
    oldID = ID_column.getCellData(index).toString();//used to update the row while id needs to be changed

    ID_txt.setText(ID_column.getCellData(index).toString());
    Type_ChoiceBox.setValue(Type_column.getCellData(index).toString());
    Category_choiceBox.setValue(Category_column.getCellData(index).toString());
    Name_txt.setText(Name_column.getCellData(index).toString());
    Location_choiceBox.setValue(Location_column.getCellData(index).toString());
    }

    @FXML
    void Add(ActionEvent event) {

        if(!ID_txt.getText().isEmpty()){
            conn = mysqlconnect.ConnectDb();
            String sql = "insert into hstuarchitecture.structural_entities(ID,Type,Category,Name,Location) values(?,?,?,?,?)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, ID_txt.getText());
                pst.setString(2, Type_ChoiceBox.getValue());
                pst.setString(3, Category_choiceBox.getValue());
                pst.setString(4, Name_txt.getText());
                pst.setString(5, Location_choiceBox.getValue());
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
            JOptionPane.showMessageDialog(null, "Missing 'ID'.");
        }
    }

    @FXML
    void Update(ActionEvent event) {
        if(!ID_txt.getText().isEmpty())
        try {
            conn = mysqlconnect.ConnectDb();
            String value1 = ID_txt.getText();
            String value2 = Type_ChoiceBox.getValue();
            String value3 = Category_choiceBox.getValue();
            String value4 = Name_txt.getText();
            String value5 = Location_choiceBox.getValue();
            String sql = "update structural_entities set ID= ? ,Type= ? ,Category= ? ,Name= ? ,Location=? where ID=? ";
            pst= conn.prepareStatement(sql);
            pst.setString(1, value1);
            pst.setString(2, value2);
            pst.setString(3, value3);
            pst.setString(4, value4);
            pst.setString(5, value5);
            pst.setString(6, oldID);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Successfully Updated in database.");
            UpdateTable();
            Search();
            //Refresh(event);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        else{
            JOptionPane.showMessageDialog(null, "ID not found.");
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        
        if(!ID_txt.getText().isEmpty()){

        //confirmation for deletion.
        String selection = "Caution: Related contents of other tables will also be deleted.";
        Alert alert = new Alert(AlertType.CONFIRMATION, selection, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setHeaderText("Are you sure you want to delete the row with ID = "+ID_txt.getText()+"?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
             //deleting from structural_entities table
            conn = mysqlconnect.ConnectDb();
            String sql1 = "delete from structural_entities where ID = ?";
            try {
                pst = conn.prepareStatement(sql1);
                pst.setString(1, ID_txt.getText());
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
        // in case the ID text field empty.
        else{
            JOptionPane.showMessageDialog(null, "ID not found.");
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

    ObservableList<structural_entities> listM;
    ObservableList<structural_entities> dataList;
    
    /// to conduct search and set table in structural_entities table
    public static ObservableList<structural_entities> getData_structural_entities(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<structural_entities> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM structural_entities ORDER BY LPAD(lower(ID), 15,0) asc;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new structural_entities(rs.getString("ID"),rs.getString("Type"),rs.getString("Category"), rs.getString("Name"), rs.getString("Location")));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    @FXML
    void Search() {

        ID_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("ID"));
        Type_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Type"));
        Category_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Category"));
        Name_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Name"));
        Location_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Location"));

        dataList = getData_structural_entities();
        table_structural_entities.setItems(dataList);
        FilteredList<structural_entities> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue )-> {
            filteredData.setPredicate(structural_entities -> {
                if(newValue==null || newValue.isEmpty())
                return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(structural_entities.getID().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(structural_entities.getType().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(structural_entities.getCategory().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(structural_entities.getName().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                if(structural_entities.getLocation().toLowerCase(). indexOf(lowerCaseFilter) !=-1)
                return true;
                else 
                return false;
            });
        });
        SortedList<structural_entities> sortedDate = new SortedList<>(filteredData);
        sortedDate.comparatorProperty().bind(table_structural_entities.comparatorProperty());
        table_structural_entities.setItems(sortedDate);
    }

    @FXML
    void AddCategory(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void AddType(ActionEvent event) throws IOException  {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddType.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        // Stage stage = (Stage)backButton.getScene().getWindow();
        // stage.close();
        // Parent root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
        // Scene scene = new Scene(root);
        // Stage primaryStage = new Stage();
        // primaryStage.setScene(scene);
        // primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("Map_Front_Page.fxml"));
        Stage stage=(Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Refresh(ActionEvent event) {
        ID_txt.clear();
        Type_ChoiceBox.getSelectionModel().clearSelection();
        Type_ChoiceBox.getItems().clear();
        Category_choiceBox.getSelectionModel().clearSelection();
        Category_choiceBox.getItems().clear();
        Name_txt.clear();
        Location_choiceBox.getSelectionModel().clearSelection();
        Location_choiceBox.getItems().clear();
        filterField.clear();

        initialize(null, null);
    }

    /// to add types to the types_choiceBox of structural_entities table 
    public static ObservableList<String> getTypes(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT types FROM type;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(rs.getString("types"));
                
            }
        } catch (Exception e) {
        }
        return list;
    }

    /// to add types to the categories_choiceBox of structural_entities table 
    public static ObservableList<String> getCategories(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT categories FROM category;");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(rs.getString("categories"));
                
            }
        } catch (Exception e) {
        }
        return list;
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       structuralEntitiesButton.setStyle("-fx-border-color: #0000ff; ");
       UpdateTable();
       Search();
       
       /// load items to Type_choiceBox
       ObservableList<String> n = getTypes();
       Type_ChoiceBox.getItems().addAll(n);

       /// load items to Category_choiceBox
       ObservableList<String> m = getCategories();
       Category_choiceBox.getItems().addAll(m);

       /// load items to Location_choiceBox
       String[] locations = {"East","West","North","South","North-east","North-west","South-east","South-west"};
       Location_choiceBox.getItems().addAll(locations);

       ///set icons
         Image imageDecline = new Image(getClass().getResourceAsStream("icons/icons8-customize-25.png"));
         addTypeButton.setGraphic(new ImageView(imageDecline));
         addCategoryButton.setGraphic(new ImageView(imageDecline));
         
         Image imageDecline1 = new Image(getClass().getResourceAsStream("icons/icons8-refresh-25.png"));
         refreshButton.setGraphic(new ImageView(imageDecline1));

         Image imageDecline2 = new Image(getClass().getResourceAsStream("icons/icons8-back-25.png"));
         backButton.setGraphic(new ImageView(imageDecline2));

        //  Image imageDecline3 = new Image(getClass().getResourceAsStream("icons/icons8-pen-25.png"));
        //  addButton.setGraphic(new ImageView(imageDecline3));

        //  Image imageDecline4 = new Image(getClass().getResourceAsStream("icons/icons8-dustbin-25.png"));
        //  deleteButton.setGraphic(new ImageView(imageDecline4));

         filterField.setStyle("-fx-background-image:url('icons/icons8-search-25.png'); -fx-background-repeat: no-repeat; -fx-background-position: right center;");
    }

    public void UpdateTable(){
        ID_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("ID"));
        Type_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Type"));
        Category_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Category"));
        Name_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Name"));
        Location_column.setCellValueFactory(new PropertyValueFactory<structural_entities,String>("Location"));
        
        
        listM = getData_structural_entities();
        table_structural_entities.setItems(listM);
        table_structural_entities.setStyle("-fx-font-size: 16;");
    }
}
