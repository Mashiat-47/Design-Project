import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField userIDField;

    public LoginScreen() {
        setTitle("SafeLife+ Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Enter User ID:", SwingConstants.CENTER));
        userIDField = new JTextField();
        panel.add(userIDField);

        JButton loginBtn = new JButton("Login");
        panel.add(loginBtn);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = userIDField.getText().trim();
                User u = DataStore.authenticateContext(id);
                if (u != null) {
                    u.login();
                    dispose();
                    if (u instanceof Admin) {
                        new AdminDashboard((Admin) u).setVisible(true);
                    } else if (u instanceof Employee) {
                        new EmployeeDashboard((Employee) u).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid ID. Try E001 or A001", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
