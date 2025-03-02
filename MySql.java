import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MySql {
   private static final String DB_URL = "jdbc:mysql://localhost:3306/unischema";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "";
   Connection conn=null;
   public static Connection ConnectionDb(){
      try {
          Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          JOptionPane.showMessageDialog(null,"Connection Established");
          return conn;
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
         return null;
      }
   }
}
