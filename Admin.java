import javax.swing.JOptionPane;

public class Admin extends User {
    
    public Admin(String userID, String name, String email) {
        super(userID, name, email);
    }

    public void markIncidentResolved(String incidentID) {
        Incident inc = DataStore.getIncidentByID(incidentID);
        if (inc != null) {
            inc.setStatus("Resolved");
        }
    }

    public void sendAlertMessage(String message) {
        DataStore.addAlert(message);
        JOptionPane.showMessageDialog(null, "System BROADCAST Sent successfully to all employees:\n" + message, "Admin Alert", JOptionPane.INFORMATION_MESSAGE);
    }
}
