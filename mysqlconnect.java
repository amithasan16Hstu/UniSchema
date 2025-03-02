import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class mysqlconnect {
    static final String DB_URL = "jdbc:mysql://localhost:3306/unischema";
    static final String USER = "root";
    static final String PASS = "";
     Connection conn = null;
    public static Connection ConnectDb(){
        try {
           
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    
    }
   
    
}

