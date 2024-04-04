package teacherpkg;

public class StudentAttendenceData {

    String present="";
    String absent="";

    String name,enr,status;



    public StudentAttendenceData(String name,String enr,String p,String ab,String status) {
        this.absent=ab;
        this.present=p;
        this.name=name;
        this.enr=enr;
        this.status=status;

    }
}
