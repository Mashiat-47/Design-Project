import java.util.ArrayList;

public class DataStore {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Incident> incidents = new ArrayList<>();
    private static ArrayList<Attendance> attendances = new ArrayList<>();
    private static ArrayList<String> alerts = new ArrayList<>();

    static {
        users.add(new Employee("E001", "John Employee", "john@safelife.com"));
        users.add(new Admin("A001", "Alice Admin", "alice@safelife.com"));
        alerts.add("Welcome to the SafeLife+ Incident Management System!");
    }

    public static void addAlert(String msg) {
        alerts.add(msg);
    }
    
    public static ArrayList<String> getAlerts() {
        return alerts;
    }

    public static User authenticateContext(String userID) {
        for (User u : users) {
             if (u.getUserID().equals(userID)) {
                 return u;
             }
        }
        return null;
    }

    public static void addIncident(Incident incident) {
        incidents.add(incident);
        System.out.println("Incident saved to DataStore.");
    }

    public static ArrayList<Incident> getAllIncidents() {
        return incidents;
    }

    public static ArrayList<Incident> getIncidentsByReporter(String reporterID) {
        ArrayList<Incident> result = new ArrayList<>();
        for (Incident inc : incidents) {
            if (inc.getReporterID().equals(reporterID)) {
                result.add(inc);
            }
        }
        return result;
    }

    public static Incident getIncidentByID(String incidentID) {
        for (Incident inc : incidents) {
            if (inc.getIncidentID().equals(incidentID)) {
                return inc;
            }
        }
        return null;
    }

    public static void addAttendance(Attendance attendance) {
        attendances.add(attendance);
        System.out.println("Attendance saved to DataStore.");
    }

    public static ArrayList<Attendance> getAllAttendance() {
        return attendances;
    }
}
