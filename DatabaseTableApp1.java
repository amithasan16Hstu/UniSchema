import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DatabaseTableApp1 extends JFrame {

    public DatabaseTableApp1() {
        setTitle("Schedule Details");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Define table columns
        String[] columnNames = {"ID", "Department", "Session", "Students", "Start Date", "End Date"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close current window
            new AllocatingPage(); // Ensure AllocatingPage class exists
        });

        // Add Back Button at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Fetch data from database
        fetchData(model);

        setVisible(true);
    }

    private void fetchData(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/unischema"; // Adjust database name if different
        String user = "root"; // Change to your MySQL username
        String password = ""; // Change to your MySQL password

        // Corrected SQL Query
        String query = "SELECT id, department, session, students, start_date, end_date FROM schedule";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String department = rs.getString("department");
                String session = rs.getString("session");
                int students = rs.getInt("students");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");

                model.addRow(new Object[]{id, department, session, students, startDate, endDate});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to launch the window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DatabaseTableApp1::new);
    }
}
