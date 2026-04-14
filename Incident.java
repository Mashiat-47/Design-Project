import java.util.UUID;

public class Incident {
    private String incidentID;
    private String description;
    private String reportedDate;
    private String status;
    private String reporterID;

    public Incident(String description, String reportedDate, String reporterID) {
        this.incidentID = "INC-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.description = description;
        this.reportedDate = reportedDate;
        this.reporterID = reporterID;
        this.status = "Pending";
    }

    public String getIncidentID() {
        return incidentID;
    }

    public String getDescription() {
        return description;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporterID() {
        return reporterID;
    }

    @Override
    public String toString() {
        return "ID: " + incidentID + "\nDate: " + reportedDate + "\nStatus: " + status + "\nDesc: " + description;
    }
}
