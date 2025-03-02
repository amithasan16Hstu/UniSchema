import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ExamRoomAllocation extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private Map<String, Integer> roomCapacities;
    private JComboBox<String> buildingDropdown, levelDropdown, semesterDropdown,shiftDropdown, roomDropdown;

    

    public ExamRoomAllocation() {
        

        roomCapacities = new HashMap<>();
        fetchRoomDataFromDatabase();
        
        //fetchRoomDataFromFile();
        //Dr. Wazed Building Rooms
        // roomCapacities.put("Room 210", 30);
        // roomCapacities.put("Room 235", 40);
        // roomCapacities.put("Room 236", 75);
        // roomCapacities.put("Room 237", 60);
        // roomCapacities.put("Room 243", 55);

        // // Dr. Kudrate Khuda Building Rooms
        // roomCapacities.put("Room 501", 45);
        // roomCapacities.put("Room 502", 40);
        // roomCapacities.put("Room 503", 45);
        // roomCapacities.put("Room 504", 50);
        // roomCapacities.put("Room 510", 60);

        // Frame Settings
        setTitle("Exam Room Allocation with Building-Specific Optimization");
        setSize(900, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        ImageIcon windowIcon = new ImageIcon("e.png"); // Replace with your icon file path
        setIconImage(windowIcon.getImage());

        

       
        // 📝 Top Panel for Inputs
        JPanel topPanel = new JPanel(new GridLayout(10, 2, 5, 5));

        topPanel.add(new JLabel("Department Name:"));
        JComboBox<String> departmentDropdown = new JComboBox<>(new String[] { "CSE", "EEE", "ECE" });
        topPanel.add(departmentDropdown);

        topPanel.add(new JLabel("Number of Students:"));
        JTextField studentsField = new JTextField();
        topPanel.add(studentsField);

        topPanel.add(new JLabel("Start Date:"));
        JTextField startDateField = new JTextField();
        topPanel.add(startDateField);

        topPanel.add(new JLabel("End Date:"));
        JTextField endDateField = new JTextField();
        topPanel.add(endDateField);
         
        topPanel.add(new JLabel("Shift:"));
        shiftDropdown = new JComboBox<>(new String[]{"Day(10 am to 1 pm)", "Morning(2 pm to 5 pm)"});
        topPanel.add(shiftDropdown);
        topPanel.add(new JLabel("Batch:"));
        JTextArea batchArea = new JTextArea(2, 20);
        JScrollPane batchScrollPane = new JScrollPane(batchArea);
        topPanel.add(batchScrollPane);

        topPanel.add(new JLabel("Select Building:"));
        buildingDropdown = new JComboBox<>(new String[] { "Dr. Wazed Building", "Dr. Kudrate Khuda Building" });
        topPanel.add(buildingDropdown);

        // New Level ComboBox
        topPanel.add(new JLabel("Select Level:"));
        levelDropdown = new JComboBox<>(new String[] { "Level 1", "Level 2", "Level 3", "Level 4", "MSc" });
        topPanel.add(levelDropdown);

        // New Semester ComboBox
        topPanel.add(new JLabel("Select Semester:"));
        semesterDropdown = new JComboBox<>(new String[] { "Semester I", "Semester II" });
        topPanel.add(semesterDropdown);

        topPanel.add(new JLabel("Select Room (Manual Allocation):"));
        roomDropdown = new JComboBox<>(roomCapacities.keySet().toArray(new String[0]));
        topPanel.add(roomDropdown);
       
        // 📊 Bottom Panel for Allocation Table
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Allocation Information Display"));

        String[] columns = {
        "Department", "Batch", "Number of Students", "Start Time", "End Time","Shift",
        "Level", "Semester", "Room Number", "Capacity", "Allocated Students"
};

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new CustomRowRenderer()); // Set custom renderer
        JScrollPane scrollPane = new JScrollPane(table);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // ✅ Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton optimizeButton = new JButton("Optimize Allocation");
        optimizeButton.setBackground(new Color(144, 238, 144));

        


        JButton deleteButton = new JButton("Delete Row");
        deleteButton.setBackground(new Color(255, 102, 102));
        // 🗑️ Delete Button Action Listener
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // Get selected row index

            if (selectedRow != -1) { // Check if a row is selected
                tableModel.removeRow(selectedRow); // Remove the selected row
                JOptionPane.showMessageDialog(this, "Row deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table after deletion
                table.clearSelection();
                table.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton updateButton = new JButton("Update Row");
        updateButton.setBackground(new Color(255, 204, 0));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(173, 216, 230));
        backButton.addActionListener(e -> {
            dispose();
            new AllocatingPage();
        });
        //JPanel buttonPanel = new JPanel();
        JButton saveButton1 = new JButton("Save Allocation");
        saveButton1.setBackground(new Color(144, 238, 144));

        buttonPanel.add(optimizeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton1);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        // ✅ Legend Panel for Color Indications
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Department Color Indications"));

        // Legend for CSE
        JPanel cseLegend = new JPanel();
        cseLegend.setBackground(new Color(173, 216, 230));
        cseLegend.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(cseLegend);
        legendPanel.add(new JLabel("CSE"));

        // Legend for EEE
        JPanel eeeLegend = new JPanel();
        eeeLegend.setBackground(new Color(144, 238, 144));
        eeeLegend.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(eeeLegend);
        legendPanel.add(new JLabel("EEE"));

        // Legend for ECE
        JPanel eceLegend = new JPanel();
        eceLegend.setBackground(new Color(255, 204, 153));
        eceLegend.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(eceLegend);
        legendPanel.add(new JLabel("ECE"));

        // Add the legend panel to the bottomPanel
        bottomPanel.add(legendPanel, BorderLayout.NORTH);

        // 🚀 Optimize Button Action Listener
        optimizeButton.addActionListener(e -> {
            try {
                int numStudents = Integer.parseInt(studentsField.getText());
                String department = departmentDropdown.getSelectedItem().toString();
                String batch = batchArea.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String shift = shiftDropdown.getSelectedItem().toString();
                String level = levelDropdown.getSelectedItem().toString();
                String semester = semesterDropdown.getSelectedItem().toString();

                optimizeRoomAllocationByBuilding(department, batch, numStudents, startDate, endDate,shift, level, semester);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid student numbers!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Panels to Frame
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        setVisible(true);

        JButton manualAllocateButton = new JButton("Manual Allocate");
        manualAllocateButton.setBackground(new Color(255, 204, 102));
        manualAllocateButton.addActionListener(e -> {
            try {
                int numStudents = Integer.parseInt(studentsField.getText());
                String department = (String)departmentDropdown.getSelectedItem();
                String batch = (String)batchArea.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String shift = (String)shiftDropdown.getSelectedItem();
                String level1 = (String)levelDropdown.getSelectedItem();
                String semester = (String)semesterDropdown.getSelectedItem();

                optimizeRoomAllocationByBuilding(department, batch, numStudents, startDate, endDate,shift, level1, semester);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid student numbers!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(manualAllocateButton);   

        saveButton1.addActionListener(e -> {
            String department = (String) departmentDropdown.getSelectedItem(); // Get selected department
            String batch = batchArea.getText();
    
            int students;
        
            try {
                students = Integer.parseInt(studentsField.getText()); // Validate student number
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number of students!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String shift=(String)shiftDropdown.getSelectedItem();
            String level1=(String)levelDropdown.getSelectedItem();
            String Semester=(String)semesterDropdown.getSelectedItem();
            String roomNumber=(String)roomDropdown.getSelectedItem();

            String url = "jdbc:mysql://localhost:3306/unischema"; // Database name
            String user = "root"; // MySQL username
            String password = ""; // MySQL password
        
            // FIXED: Removed `id` because MySQL will auto-generate it
            String insertQuery = "INSERT INTO examschedule2 (department, batch, students, startDate, endDate, shift, level1, semester, roomNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery, java.sql.Statement.RETURN_GENERATED_KEYS)) { // FIXED: Explicit import
        
                pstmt.setString(1, department);
                pstmt.setString(2, batch);
                pstmt.setInt(3, students);
                pstmt.setString(4, startDate);
                pstmt.setString(5, endDate);
                pstmt.setString(6, shift);
                pstmt.setString(7, level1);
                pstmt.setString(8, Semester);
                pstmt.setString(9, roomNumber);
                //pstmt.setInt(3, capacity);
             
        
                int affectedRows = pstmt.executeUpdate();
        
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1); // Get the auto-generated ID
                            JOptionPane.showMessageDialog(null, "Data saved successfully! ID: " + generatedId, "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
        
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        

    }
    private void fetchRoomDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/unischema";
        String user = "root";
        String password = "";

        String query = "SELECT room_number, capacity FROM room_capacities";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            roomCapacities.clear();

            while (rs.next()) {
                roomCapacities.put(rs.getString("room_number"), rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching room data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void optimizeRoomAllocationByBuilding(String department, String batch, int numStudents, String startDate,
            String endDate,String shift, String level, String semester) {
        String selectedBuilding = (String) buildingDropdown.getSelectedItem();
        List<RoomAllocation> roomList = new ArrayList<>();

        if ("Dr. Wazed Building".equals(selectedBuilding)) {
            addRoomsToList(roomList, new String[] { "Room 210", "Room 235", "Room 236", "Room 237", "Room 243" });
        } else {
            addRoomsToList(roomList, new String[] { "Room 501", "Room 502", "Room 503", "Room 504", "Room 510" });
        }

        for (RoomAllocation room : roomList) {
            if (numStudents <= 0)
                break;
            int allocatedStudents = Math.min(numStudents, room.capacity);
            tableModel.addRow(new Object[] { department, batch, numStudents, startDate, endDate,shift, level, semester,
                    room.roomName, room.capacity, allocatedStudents });
            numStudents -= allocatedStudents;
        }
    }

    private void addRoomsToList(List<RoomAllocation> roomList, String[] rooms) {
        for (String room : rooms) {
            roomList.add(new RoomAllocation(room, roomCapacities.get(room)));
        }
    }

    private static class RoomAllocation {
        String roomName;
        int capacity;

        RoomAllocation(String roomName, int capacity) {
            this.roomName = roomName;
            this.capacity = capacity;
        }
    }

    // Custom Renderer for Row Colors
    private static class CustomRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String department = table.getValueAt(row, 0).toString();

            if (department.equals("CSE"))
                cell.setBackground(new Color(173, 216, 230));
            else if (department.equals("EEE"))
                cell.setBackground(new Color(144, 238, 144));
            else if (department.equals("ECE"))
                cell.setBackground(new Color(255, 204, 153));
            else
                cell.setBackground(Color.WHITE);

            return cell;
        }
    }

    public static void main(String[] args) {
        new ExamRoomAllocation();
    }
}
