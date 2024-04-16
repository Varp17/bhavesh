package teacherpkg;

public class StudentAttendenceData {

    String name;
    String enr;
    String status;

    public StudentAttendenceData(String name, String enr, String status) {
        this.name = name;
        this.enr = enr;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getEnrollment() {
        return enr;
    }

    public String getStatus() {
        return status;
    }
}
