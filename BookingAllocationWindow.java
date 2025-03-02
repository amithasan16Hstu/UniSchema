import javax.swing.*;
import java.awt.*;

public class BookingAllocationWindow {

    public static void main(String[] args) {
        // Main Frame
        JFrame frame = new JFrame("Booking Allocation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(173, 216, 230)); // Light Blue Header
        headerPanel.add(new JLabel("<html><h2>Booking Allocation</h2></html>"));

        // Criteria Panel
        JPanel criteriaPanel = new JPanel();
        criteriaPanel.setBorder(BorderFactory.createTitledBorder("Booking Criteria"));
        criteriaPanel.setLayout(new GridLayout(3, 2, 10, 10));

        criteriaPanel.add(new JLabel("Date Range:"));
        JPanel datePanel = new JPanel();
        datePanel.add(new JTextField(10)); // Start Date
        datePanel.add(new JLabel("to"));
        datePanel.add(new JTextField(10)); // End Date
        criteriaPanel.add(datePanel);

        criteriaPanel.add(new JLabel("Room Type:"));
        criteriaPanel.add(new JComboBox<>(new String[]{"Single", "Double", "Suite"}));

        criteriaPanel.add(new JLabel("Category:"));
        criteriaPanel.add(new JComboBox<>(new String[]{"Standard", "Deluxe"}));

        // Booking Selection Panel
        JPanel bookingPanel = new JPanel();
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Select Booking"));
        bookingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        bookingPanel.add(new JLabel("Select Booking:"));
        bookingPanel.add(new JComboBox<>(new String[]{"Booking 1", "Booking 2"}));
        bookingPanel.add(new JButton("Browse"));
        bookingPanel.add(new JButton("Upload"));

        // Room Allocation Table
        String[] columns = {"#", "Room Type", "Category", "Reserved", "Allocated"};
        Object[][] data = {
                {"1", "Student Accommodation", "Room - 1 Person", "1", "0"}
        };
        JTable table = new JTable(data, columns);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Auto Allocate Rooms"));
        buttonPanel.add(new JButton("Unallocate Rooms"));

        // Footer Note
        JLabel footerNote = new JLabel("Guest names in bold text are yet to be allocated.");

        // Adding Components to Main Panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(criteriaPanel, BorderLayout.WEST);
        mainPanel.add(bookingPanel, BorderLayout.CENTER);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(footerNote, BorderLayout.PAGE_END);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
