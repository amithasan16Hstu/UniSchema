import javax.swing.*;
import java.awt.*;

public class LoginPage {

    public void loginPage() {
        // 🖥️ Frame Setup
        JFrame frame = new JFrame("Dean Administration");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // 🔹 Set Window Icon
        ImageIcon windowIcon = new ImageIcon(getClass().getResource("/key.png")); // Ensure the image is inside src folder
        frame.setIconImage(windowIcon.getImage());

        // 🟦 Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 250, 600);
        leftPanel.setBackground(new Color(0, 153, 204));
        leftPanel.setLayout(null);

        JLabel devLabel = new JLabel("DEAN ADMINISTRATION");
        devLabel.setBounds(30, 50, 200, 30);
        devLabel.setForeground(Color.WHITE);
        devLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(devLabel);

        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/Login.png"))); // Load Image properly
        iconLabel.setBounds(70, 100, 100, 100);
        leftPanel.add(iconLabel);

        JLabel facultyLabel = new JLabel("<html><center>Faculty of CSE, HSTU<br>Dinajpur-5200</center></html>");
        facultyLabel.setBounds(30, 300, 200, 100);
        facultyLabel.setForeground(Color.WHITE);
        facultyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leftPanel.add(facultyLabel);

        frame.add(leftPanel);

        // 📝 Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(250, 0, 350, 600);
        rightPanel.setLayout(null);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(130, 30, 100, 30);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 26));
        loginLabel.setForeground(new Color(0, 153, 204));
        rightPanel.add(loginLabel);

        JLabel usernameLabel = new JLabel("Enter Email:");
        usernameLabel.setBounds(50, 80, 200, 20);
        rightPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 110, 250, 30);
        rightPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(50, 150, 200, 20);
        rightPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 180, 200, 30);
        rightPanel.add(passwordField);

        JButton eyeButton = new JButton(new ImageIcon(getClass().getResource("/EYE.png")));
        eyeButton.setBounds(260, 180, 40, 30);
        eyeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eyeButton.setFocusPainted(false);
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setToolTipText("See Password");
        rightPanel.add(eyeButton);

        eyeButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '\u0000') {
                passwordField.setEchoChar('*');
                eyeButton.setToolTipText("See Password");
            } else {
                passwordField.setEchoChar('\u0000');
                eyeButton.setToolTipText("Hide Password");
            }
        });

        JLabel verificationCodeLabel = new JLabel("Enter Verification Code:");
        verificationCodeLabel.setBounds(50, 220, 200, 20);
        rightPanel.add(verificationCodeLabel);

        JTextField verificationCodeField = new JTextField();
        verificationCodeField.setBounds(50, 250, 250, 30);
        rightPanel.add(verificationCodeField);

        JLabel reVerificationCodeLabel = new JLabel("Re-enter Verification Code:");
        reVerificationCodeLabel.setBounds(50, 290, 200, 20);
        rightPanel.add(reVerificationCodeLabel);

        JTextField reVerificationCodeField = new JTextField();
        reVerificationCodeField.setBounds(50, 320, 250, 30);
        rightPanel.add(reVerificationCodeField);

        // 🔹 Login Button (Light Blue)
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 370, 100, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(173, 216, 230)); // Light Blue
        loginButton.setOpaque(true);
        rightPanel.add(loginButton);

        // 🔴 Exit Button (Light Red)
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 370, 100, 30);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(new Color(255, 160, 122)); // Light Red
        exitButton.setOpaque(true);
        rightPanel.add(exitButton);

        // 🔹 Forgot Password Button
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(50, 420, 250, 30);
        forgotPasswordButton.setForeground(Color.BLUE);
        forgotPasswordButton.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        rightPanel.add(forgotPasswordButton);

        frame.add(rightPanel);

        // 🎯 Forgot Password Button Action
        forgotPasswordButton.addActionListener(e -> showForgotPasswordDialog(frame));

        // 🎯 Login Button Action
        loginButton.addActionListener(e -> {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String verificationCode = verificationCodeField.getText();
            String reVerificationCode = reVerificationCodeField.getText();

            if (email.isEmpty() || password.isEmpty() || verificationCode.isEmpty() || reVerificationCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!verificationCode.equals(reVerificationCode)) {
                JOptionPane.showMessageDialog(frame, "Verification codes do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (validateCredentials(email, password, verificationCode)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new AllocatingPage(); // Ensure AllocatingPage class exists
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        exitButton.addActionListener(e -> frame.dispose());
        frame.setVisible(true);
    }

    private void showForgotPasswordDialog(JFrame parentFrame) {
        String email = JOptionPane.showInputDialog(parentFrame, "Enter your registered email:", "Forgot Password", JOptionPane.QUESTION_MESSAGE);
        if (email != null && !email.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Wait for Admin! A password reset link has been sent to: " + email, "Reset Password", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateCredentials(String email, String password, String verificationCode) {
        // TODO: Replace with database authentication logic
        return email.equals("dean.cse@hstu.ac.bd.com") && password.equals("12345") && verificationCode.equals("9999");
    }

    public static void main(String[] args) {
        new LoginPage().loginPage();
        //SwingUtilities.invokeLater(LoginPage::new);
    }
}
