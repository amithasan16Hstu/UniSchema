import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class classroom {

    private static HashMap<String, String> roomAllocation = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Room Allocation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for Batch Room Allocation
        JPanel batchPanel = new JPanel();
        batchPanel.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel deptLabel = new JLabel("Department Name:");
        JTextField deptField = new JTextField();

        JLabel sessionLabel = new JLabel("Session:");
        JTextField sessionField = new JTextField();

        JLabel studentsLabel = new JLabel("Number of Students:");
        JTextField studentsField = new JTextField();

        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateField = new JTextField();

        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateField = new JTextField();

        JButton allocateClassroomButton = new JButton("Allocate Classroom");
        JButton allocateExamRoomButton = new JButton("Allocate Exam Room");
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        batchPanel.add(deptLabel);
        batchPanel.add(deptField);
        batchPanel.add(sessionLabel);
        batchPanel.add(sessionField);
        batchPanel.add(studentsLabel);
        batchPanel.add(studentsField);
        batchPanel.add(startDateLabel);
        batchPanel.add(startDateField);
        batchPanel.add(endDateLabel);
        batchPanel.add(endDateField);
        batchPanel.add(allocateClassroomButton);
        batchPanel.add(allocateExamRoomButton);
        batchPanel.add(new JLabel());
        batchPanel.add(messageLabel);

        allocateClassroomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dept = deptField.getText();
                String session = sessionField.getText();
                String students = studentsField.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();

                String key = dept + "-" + session;
                if (roomAllocation.containsKey(key)) {
                    messageLabel.setText("Room already allocated: " + roomAllocation.get(key));
                } else {
                    String allocatedRoom = "Building 1, Room 101"; // Example
                    roomAllocation.put(key, allocatedRoom);
                    messageLabel.setText("Allocated Room: " + allocatedRoom);
                }
            }
        });

        allocateExamRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dept = deptField.getText();
                String session = sessionField.getText();
                String students = studentsField.getText();

                try {
                    int numStudents = Integer.parseInt(students);
                    String allocatedRoom = "Building 2, Room 202"; // Example
                    messageLabel.setText("Exam Room: " + allocatedRoom + ", Capacity: " + numStudents);
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid number of students.");
                }
            }
        });

        // Panel for Room Selection
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new BorderLayout());

        DefaultListModel<String> roomListModel = new DefaultListModel<>();
        roomListModel.addElement("Building 1 > Room 101 (Capacity: 60)");
        roomListModel.addElement("Building 1 > Room 102 (Capacity: 50)");
        roomListModel.addElement("Building 2 > Room 201 (Capacity: 70)");

        JList<String> roomList = new JList<>(roomListModel);
        JScrollPane scrollPane = new JScrollPane(roomList);

        JButton confirmAllocationButton = new JButton("Confirm Allocation");
        confirmAllocationButton.setEnabled(false);

        roomList.addListSelectionListener(e -> {
            confirmAllocationButton.setEnabled(!roomList.isSelectionEmpty());
        });

        confirmAllocationButton.addActionListener(e -> {
            String selectedRoom = roomList.getSelectedValue();
            messageLabel.setText("Room Confirmed: " + selectedRoom);
        });

        roomPanel.add(scrollPane, BorderLayout.CENTER);
        roomPanel.add(confirmAllocationButton, BorderLayout.SOUTH);

        // Adding tabs to the tabbedPane
        tabbedPane.addTab("Allocate Room", batchPanel);
        tabbedPane.addTab("Select Room", roomPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
