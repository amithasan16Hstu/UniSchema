import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DatabaseTableApp extends JFrame {

    public DatabaseTableApp() {
        setTitle("Building Rooms Details");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table setup with proper column names
        String[] columnNames = {"Room ID", "Floor ID", "Room Number", "Room Type", "Room Size"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close current window
            new AllocatingPage(); // Open AllocatingPage window
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

        String query = "SELECT room_id, floor_id, room_number, room_type, room_size FROM building_rooms";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                int floorId = rs.getInt("floor_id");
                int roomNumber = rs.getInt("room_number");
                String roomType = rs.getString("room_type");
                int roomSize = rs.getInt("room_size");

                model.addRow(new Object[]{roomId, floorId, roomNumber, roomType, roomSize});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
