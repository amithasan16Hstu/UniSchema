import javax.swing.*;
import java.awt.*;

public class AllocatingPage {
    public AllocatingPage() {
        // Create the main frame
        JFrame frame = new JFrame("Allocating Page");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        ImageIcon windowIcon = new ImageIcon(getClass().getResource("/allo.png"));
        frame.setIconImage(windowIcon.getImage());
//ImageIcon windowIcon = new ImageIcon(getClass().getResource("/key.png"));
        // === LEFT PANEL ===
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 200, 600);
        leftPanel.setBackground(new Color(135, 206, 250)); // Sky Blue
        leftPanel.setLayout(null);

        // Dashboard Icon and Text at Top of Sidebar
        ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("/DASHBOARD.png"));// Replace with your dashboard icon
        Image dashboardImg = dashboardIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel dashboardLabel = new JLabel("Dashboard", new ImageIcon(dashboardImg), JLabel.LEFT);
        dashboardLabel.setBounds(20, 10, 160, 40);
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardLabel.setForeground(Color.BLACK);
        dashboardLabel.setIconTextGap(10);
        leftPanel.add(dashboardLabel);

        // Allocate Classroom Button
        ImageIcon classIcon = new ImageIcon(getClass().getResource("/classroom.png")); // Replace with your icon file
        Image classImg = classIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedClassIcon = new ImageIcon(classImg);

        JButton allocateClassButtonLeft = new JButton("<html>Allocate<br>Classroom</html>", resizedClassIcon);
        allocateClassButtonLeft.setBounds(20, 70, 160, 50);
        allocateClassButtonLeft.setForeground(new Color(0, 0, 0));
        allocateClassButtonLeft.setBorderPainted(false);
        allocateClassButtonLeft.setContentAreaFilled(false);
        allocateClassButtonLeft.setCursor(new Cursor(Cursor.HAND_CURSOR));
        allocateClassButtonLeft.setFont(new Font("Arial", Font.BOLD, 14));
        allocateClassButtonLeft.setHorizontalAlignment(SwingConstants.LEFT);
        allocateClassButtonLeft.setIconTextGap(10); // Space between icon and text
        leftPanel.add(allocateClassButtonLeft);

        allocateClassButtonLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allocateClassButtonLeft.setForeground(new Color(0, 255, 255)); // Cyan on Hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                allocateClassButtonLeft.setForeground(new Color(0, 0, 0)); // Reset to Black
            }
        });

        // Allocate Examroom Button
        ImageIcon examIcon = new ImageIcon(getClass().getResource("/EXA.png")); // Replace with your icon file
        Image examImg = examIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedExamIcon = new ImageIcon(examImg);

        JButton allocateExamButtonLeft = new JButton("<html>Allocate<br>Examroom</html>", resizedExamIcon);
        allocateExamButtonLeft.setBounds(20, 130, 160, 50);
        allocateExamButtonLeft.setForeground(new Color(0, 0, 0));
        allocateExamButtonLeft.setBorderPainted(false);
        allocateExamButtonLeft.setContentAreaFilled(false);
        allocateExamButtonLeft.setCursor(new Cursor(Cursor.HAND_CURSOR));
        allocateExamButtonLeft.setFont(new Font("Arial", Font.BOLD, 14));
        allocateExamButtonLeft.setHorizontalAlignment(SwingConstants.LEFT);
        allocateExamButtonLeft.setIconTextGap(10); // Space between icon and text
        leftPanel.add(allocateExamButtonLeft);
        allocateExamButtonLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allocateExamButtonLeft.setForeground(new Color(0, 255, 255)); // Cyan on Hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                allocateExamButtonLeft.setForeground(new Color(0, 0, 0)); // Reset to Black
            }
        });
        

        // Room Details Button
ImageIcon roomIcon = new ImageIcon(getClass().getResource("/plans.png")); // Replace with your room details icon
Image roomImg = roomIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
ImageIcon resizedRoomIcon = new ImageIcon(roomImg);

JButton roomDetailsButton = new JButton("<html>Room Details</html>", resizedRoomIcon);
roomDetailsButton.setBounds(20, 190, 160, 50); // Adjust vertical position if needed
roomDetailsButton.setForeground(new Color(0, 0, 0)); // Black text
roomDetailsButton.setBorderPainted(false);
roomDetailsButton.setContentAreaFilled(false);
roomDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
roomDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));
roomDetailsButton.setHorizontalAlignment(SwingConstants.LEFT);
roomDetailsButton.setIconTextGap(10); // Space between icon and text



// Hover Effect (Optional)
roomDetailsButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        roomDetailsButton.setForeground(new Color(0, 255, 255)); // Cyan on Hover
    }
    public void mouseExited(java.awt.event.MouseEvent evt) {
        roomDetailsButton.setForeground(new Color(0, 0, 0)); // Reset to Black
    }
});
roomDetailsButton.addActionListener(e -> {
    frame.dispose();
    new DatabaseTableApp();
});



leftPanel.add(roomDetailsButton);

// Previous Allocation Details Button
ImageIcon previousIcon = new ImageIcon(getClass().getResource("/prev.png")); // Replace with your previous allocation details icon
Image previousImg = previousIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
ImageIcon resizedPreviousIcon = new ImageIcon(previousImg);

JButton previousAllocationButton = new JButton("<html>Previous<br>Allocation Details</html>", resizedPreviousIcon);
previousAllocationButton.setBounds(20, 250, 160, 50); // Adjust vertical position if needed
previousAllocationButton.setForeground(new Color(0, 0, 0)); // Black text
previousAllocationButton.setBorderPainted(false);
previousAllocationButton.setContentAreaFilled(false);
previousAllocationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
previousAllocationButton.setFont(new Font("Arial", Font.BOLD, 14));
previousAllocationButton.setHorizontalAlignment(SwingConstants.LEFT);
previousAllocationButton.setIconTextGap(10); // Space between icon and text

// Hover Effect (Optional)
previousAllocationButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        previousAllocationButton.setForeground(new Color(0, 255, 255)); // Cyan on Hover
    }
    public void mouseExited(java.awt.event.MouseEvent evt) {
        previousAllocationButton.setForeground(new Color(0, 0, 0)); // Reset to Black
    }
});
previousAllocationButton.addActionListener(e -> {
    frame.dispose();
    new DatabaseTableApp1();
});


leftPanel.add(previousAllocationButton);


// Log Out Button
ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/out.png")); // Replace with your logout icon
Image logoutImg = logoutIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
ImageIcon resizedLogoutIcon = new ImageIcon(logoutImg);

JButton logoutButton = new JButton("<html>Log Out</html>", resizedLogoutIcon);
logoutButton.setBounds(20, 410, 160, 50); // Adjust vertical position if needed
logoutButton.setForeground(new Color(0, 0, 0)); // Black text
logoutButton.setBorderPainted(false);
logoutButton.setContentAreaFilled(false);
logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
logoutButton.setIconTextGap(10); // Space between icon and text

// Hover Effect (Optional)
logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        logoutButton.setForeground(new Color(255, 69, 0)); // Orange-Red on Hover
    }
    public void mouseExited(java.awt.event.MouseEvent evt) {
        logoutButton.setForeground(new Color(0, 0, 0)); // Reset to Black
    }
});

// Add ActionListener for Logout Action
logoutButton.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(null, 
        "Are you sure you want to log out?", 
        "Log Out", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(null, "Successfully Logged Out!");
        System.exit(0); // Close the application
    }
});

leftPanel.add(logoutButton);


        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 102, 102));
        backButton.setForeground(new Color(0, 0, 0));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(backButton);

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setForeground(new Color(0, 255, 255)); // Cyan on Hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setForeground(new Color(0, 0, 0)); // Reset to Black
            }
        });

        frame.add(leftPanel);
// === HEADER PANEL (Beige Background) ===
JPanel headerPanel = new JPanel();
headerPanel.setBounds(200, 0, 700, 60);
headerPanel.setBackground(new Color(245, 245, 220)); // Beige
headerPanel.setLayout(null);

// Search Bar (Aligned Left)
JTextField searchField = new JTextField("Search...");
searchField.setBounds(300, 15, 200, 30); // Positioned on the far left
searchField.setFont(new Font("Arial", Font.PLAIN, 14));
headerPanel.add(searchField);

searchField.addActionListener(e -> {
    String query = searchField.getText();
    JOptionPane.showMessageDialog(null, "Searching for: " + query, "Search", JOptionPane.INFORMATION_MESSAGE);
});

// Settings Icon (Right Aligned)
ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/setting.png"));// Replace with your settings icon
Image settingsImg = settingsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
JButton settingsButton = new JButton(new ImageIcon(settingsImg));
settingsButton.setBounds(540, 15, 30, 30); // Positioned
settingsButton.setToolTipText("Settings");
settingsButton.setBorderPainted(false);
settingsButton.setContentAreaFilled(false);
settingsButton.setFocusPainted(false);
settingsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Original size for reference
int originalWidth2 = 30;
int originalHeight2 = 30;

settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Enlarge the icon on hover
        Image enlargedImg = settingsIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        settingsButton.setIcon(new ImageIcon(enlargedImg));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        // Reset to original size when the mouse exits
        Image originalImg = settingsIcon.getImage().getScaledInstance(originalWidth2, originalHeight2, Image.SCALE_SMOOTH);
        settingsButton.setIcon(new ImageIcon(originalImg));
    }
});

settingsButton.addActionListener(e -> {
    JOptionPane.showMessageDialog(null, "Settings Clicked!", "Settings", JOptionPane.INFORMATION_MESSAGE);
});

headerPanel.add(settingsButton);


// Notification Icon (Right Aligned, Next to Settings)
ImageIcon bellIcon = new ImageIcon(getClass().getResource("/BELL.png"));  // Replace with your notification icon
Image bellImg = bellIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
JButton notificationButton = new JButton(new ImageIcon(bellImg));
notificationButton.setBounds(580, 15, 30, 30); // Positioned next to Settings
notificationButton.setToolTipText("View Notifications");
notificationButton.setBorderPainted(false);
notificationButton.setContentAreaFilled(false);
notificationButton.setFocusPainted(false);
notificationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Original size for reference
int originalWidth = 30;
int originalHeight = 30;

notificationButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Enlarge the icon on hover
        Image enlargedImg = bellIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        notificationButton.setIcon(new ImageIcon(enlargedImg));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        // Reset to original size when the mouse exits
        Image originalImg = bellIcon.getImage().getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);
        notificationButton.setIcon(new ImageIcon(originalImg));
    }
});

notificationButton.addActionListener(e -> {
    JOptionPane.showMessageDialog(null,
            "You have 3 new notifications:\n1. Class allocation updated\n2. Exam room available\n3. System maintenance tonight",
            "Notifications", JOptionPane.INFORMATION_MESSAGE);
});

headerPanel.add(notificationButton);


ImageIcon profileIcon = new ImageIcon(getClass().getResource("/PROFILE.png"));// Replace with your profile icon
Image profileImg = profileIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
JButton profileButton = new JButton(new ImageIcon(profileImg));
profileButton.setBounds(620, 15, 30, 30); // Positioned next to Notification
profileButton.setToolTipText("View Profile");
profileButton.setBorderPainted(false);
profileButton.setContentAreaFilled(false);
profileButton.setFocusPainted(false);
profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Original size for reference
int originalWidth1 = 30;
int originalHeight1 = 30;

profileButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        // Enlarge the image on hover
        Image enlargedImg = profileIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        profileButton.setIcon(new ImageIcon(enlargedImg));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        // Reset to original size when the mouse exits
        Image originalImg = profileIcon.getImage().getScaledInstance(originalWidth1, originalHeight1, Image.SCALE_SMOOTH);
        profileButton.setIcon(new ImageIcon(originalImg));
    }
});

profileButton.addActionListener(e -> {
    JOptionPane.showMessageDialog(null,
            "User Profile:\nName: Dr. Md. Abdulla Al Mamun\nRole: Admin\nEmail: mamun@hstu.ac.bd",
            "Profile", JOptionPane.INFORMATION_MESSAGE);
});

headerPanel.add(profileButton);


// Add header panel to frame
frame.add(headerPanel);


        // === MAIN PANEL (Off-White Background) ===
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(200, 60, 700, 540);
        mainPanel.setBackground(new Color(255, 255, 255)); // White

        mainPanel.setLayout(null);

        JButton allocateClassButtonMain = new JButton("<html>Allocate<br>Classroom</html>");
        allocateClassButtonMain.setBounds(150, 150, 150, 150);
        allocateClassButtonMain.setFont(new Font("Arial", Font.BOLD, 14));
        allocateClassButtonMain.setForeground(new Color(0, 0, 0));
        allocateClassButtonMain.setBackground(new Color(135, 206, 250)); // Sky Blue
        allocateClassButtonMain.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(allocateClassButtonMain);

        allocateClassButtonMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allocateClassButtonMain.setForeground(new Color(0, 0, 139));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                allocateClassButtonMain.setForeground(new Color(0, 0, 0)); // Reset to Black
            }
        });
        
        

        allocateClassButtonMain.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new RoomAllocationApp());
        });

        JButton allocateExamButtonMain = new JButton("<html>Allocate<br>Examroom</html>");
        allocateExamButtonMain.setBounds(350, 150, 150, 150);
        allocateExamButtonMain.setFont(new Font("Arial", Font.BOLD, 14));
        
        allocateExamButtonMain.setForeground(new Color(0, 0, 0));
        allocateExamButtonMain.setBackground(new Color(135, 206, 250)); // Sky Blue
        allocateExamButtonMain.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(allocateExamButtonMain);

        allocateExamButtonMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allocateExamButtonMain.setForeground(new Color(0, 0, 139));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                allocateExamButtonMain.setForeground(new Color(0, 0, 0)); // Reset to Black
            }
        });

        allocateExamButtonMain.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new ExamRoomAllocation());
        });
        frame.add(mainPanel);

        // === ACTION LISTENERS ===
        allocateClassButtonLeft.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new RoomAllocationApp());
        });

        allocateExamButtonLeft.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new ExamRoomAllocation());
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage().loginPage();
        });
        

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AllocatingPage::new);
    }
}
