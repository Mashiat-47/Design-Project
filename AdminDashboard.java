import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {
    private Admin admin;
    private JTextArea allReportsArea;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setTitle("Admin Dashboard - " + admin.getName());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        JButton viewAttendanceBtn = new JButton("View Attendance");
        JButton markResolvedBtn = new JButton("Mark Incident Resolved");
        JButton sendAlertBtn = new JButton("Send Alert to All");
        JButton refreshBtn = new JButton("Refresh Reports");
        JButton logoutBtn = new JButton("Logout");

        topPanel.add(viewAttendanceBtn);
        topPanel.add(markResolvedBtn);
        topPanel.add(sendAlertBtn);
        topPanel.add(refreshBtn);
        topPanel.add(logoutBtn);

        add(topPanel, BorderLayout.NORTH);

        allReportsArea = new JTextArea();
        allReportsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(allReportsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Incident Reports"));
        add(scrollPane, BorderLayout.CENTER);

        viewAttendanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Attendance> atts = DataStore.getAllAttendance();
                StringBuilder sb = new StringBuilder();
                if (atts.isEmpty()) {
                    sb.append("No attendance records found.");
                } else {
                    for (Attendance a : atts) {
                        sb.append(a.toString()).append("\n");
                    }
                }
                JTextArea attArea = new JTextArea(15, 30);
                attArea.setText(sb.toString());
                attArea.setEditable(false);
                JOptionPane.showMessageDialog(AdminDashboard.this, new JScrollPane(attArea), "All Attendance Logs", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllReports();
            }
        });

        markResolvedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsResolvedAction();
            }
        });

        sendAlertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = JOptionPane.showInputDialog(AdminDashboard.this, "Enter alert message to broadcast:");
                if (message != null && !message.trim().isEmpty()) {
                    admin.sendAlertMessage(message);
                }
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.logout();
                dispose();
                new LoginScreen().setVisible(true);
            }
        });

        loadAllReports();
    }

    private void loadAllReports() {
        allReportsArea.setText("");
        ArrayList<Incident> allIncs = DataStore.getAllIncidents();
        if (allIncs.isEmpty()) {
            allReportsArea.append("No incidents reported yet.");
        } else {
            for (Incident inc : allIncs) {
                allReportsArea.append("Reporter ID: " + inc.getReporterID() + "\n" + inc.toString() + "\n-------------------------\n");
            }
        }
    }

    private void markAsResolvedAction() {
        String incID = JOptionPane.showInputDialog(this, "Enter the exact Incident ID to mark as resolved:");
        if (incID != null && !incID.trim().isEmpty()) {
            Incident inc = DataStore.getIncidentByID(incID.trim());
            if (inc != null) {
                admin.markIncidentResolved(incID.trim());
                JOptionPane.showMessageDialog(this, "Incident marked as Resolved!");
                loadAllReports();
            } else {
                JOptionPane.showMessageDialog(this, "Incident ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
