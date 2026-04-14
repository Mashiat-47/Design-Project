public class Attendance {
    private String employeeID;
    private String date;
    private String status;

    public Attendance(String employeeID, String date, String status) {
        this.employeeID = employeeID;
        this.date = date;
        this.status = status;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Emp ID: " + employeeID + " | Date: " + date + " | Status: " + status;
    }
}
