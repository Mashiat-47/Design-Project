import javax.swing.JOptionPane;

public class Employee extends User {

    public Employee(String userID, String name, String email) {
        super(userID, name, email);
    }

    public void reportIncident(String description, String date) {
        Incident newIncident = new Incident(description, date, this.userID);
        DataStore.addIncident(newIncident);
    }

    public void receiveAlert(String message) {
        JOptionPane.showMessageDialog(null, "Alert for " + name + ":\n" + message, "Employee Alert", JOptionPane.INFORMATION_MESSAGE);
    }
}
