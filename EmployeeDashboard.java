import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class EmployeeDashboard extends JFrame {
    private Employee employee;
    private JTextArea reportArea;

    public EmployeeDashboard(Employee employee) {
        this.employee = employee;
        setTitle("Employee Dashboard - " + employee.getName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Actions
        JPanel topPanel = new JPanel();
        JButton viewHealthBtn = new JButton("View Health Score");
        JButton viewAlertsBtn = new JButton("View Alerts");
        JButton markAttendanceBtn = new JButton("Mark Attendance");
        JButton submitIncidentBtn = new JButton("Submit New Incident");
        JButton refreshBtn = new JButton("Refresh Reports");
        JButton logoutBtn = new JButton("Logout");

        topPanel.add(viewHealthBtn);
        topPanel.add(viewAlertsBtn);
        topPanel.add(markAttendanceBtn);
        topPanel.add(submitIncidentBtn);
        topPanel.add(refreshBtn);
        topPanel.add(logoutBtn);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: View Reports
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("My Submitted Incidents"));
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        viewHealthBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate some mock health data for demonstration
                HealthData hd = new HealthData(85.5, 30.0, 95.0);
                double combinedScore = hd.calculateCombinedScore();
                String message = String.format("Employee Health Overview:\n\nAvg Health: %.1f\nAvg Stress: %.1f\nBreak Adherence: %.1f%%\n\nOverall SafeLife Score: %.1f", 
                        hd.getAverageHealthScore(), hd.getAverageStressScore(), hd.getBreakAdherencePercentage(), combinedScore);
                
                JOptionPane.showMessageDialog(EmployeeDashboard.this, message, "My Health Score", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        viewAlertsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> alerts = DataStore.getAlerts();
                StringBuilder sb = new StringBuilder();
                if (alerts.isEmpty()) {
                    sb.append("No alerts at this time.");
                } else {
                    for (String alert : alerts) {
                        sb.append("- ").append(alert).append("\n\n");
                    }
                }
                
                JTextArea alertArea = new JTextArea(10, 30);
                alertArea.setText(sb.toString());
                alertArea.setEditable(false);
                alertArea.setLineWrap(true);
                alertArea.setWrapStyleWord(true);
                
                JOptionPane.showMessageDialog(EmployeeDashboard.this, new JScrollPane(alertArea), "System Alerts", JOptionPane.WARNING_MESSAGE);
            }
        });

        markAttendanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel attPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Present", "Absent"});
                
                attPanel.add(new JLabel("Date (yyyy-MM-dd):"));
                attPanel.add(dateField);
                attPanel.add(new JLabel("Status:"));
                attPanel.add(statusCombo);
                
                int result = JOptionPane.showConfirmDialog(EmployeeDashboard.this, attPanel, "Mark Attendance", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String date = dateField.getText().trim();
                    String status = (String) statusCombo.getSelectedItem();
                    if (!date.isEmpty() && status != null) {
                        Attendance att = new Attendance(employee.getUserID(), date, status);
                        DataStore.addAttendance(att);
                        JOptionPane.showMessageDialog(EmployeeDashboard.this,
                                "Attendance marked for " + date + " as " + status,
                                "Attendance Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(EmployeeDashboard.this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        submitIncidentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubmitIncidentDialog();
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReports();
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee.logout();
                dispose();
                new LoginScreen().setVisible(true);
            }
        });

        // Initial Load
        loadReports();
    }

    private void loadReports() {
        reportArea.setText("");
        ArrayList<Incident> myIncidents = DataStore.getIncidentsByReporter(employee.getUserID());
        if (myIncidents.isEmpty()) {
            reportArea.append("No incidents reported yet.");
        } else {
            for (Incident inc : myIncidents) {
                reportArea.append(inc.toString() + "\n-------------------------\n");
            }
        }
    }

    private void openSubmitIncidentDialog() {
        JTextField descField = new JTextField(20);
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 20);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Date (yyyy-MM-dd):"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Submit Incident", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String desc = descField.getText().trim();
            String date = dateField.getText().trim();
            if (!desc.isEmpty() && !date.isEmpty()) {
                employee.reportIncident(desc, date);
                JOptionPane.showMessageDialog(this, "Incident submitted successfully!");
                loadReports();
            } else {
                JOptionPane.showMessageDialog(this, "Description and Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
