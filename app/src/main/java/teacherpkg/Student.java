package teacherpkg;

import androidx.appcompat.app.WindowDecorActionBar;

public class Student {
    public String studentname;
    private String userId,pass,email,enrollment;

    public Student(String studentname,String userid,String pass,String email,String enrollment) {
        this.studentname = studentname;
        this.userId = userid;
        this.pass=pass;
        this.email=email;
        this.enrollment=enrollment;
    }

    public String getStudentname() {
        return studentname;
    }
    public String getStudentId(){
        return userId;
    }
    public String getpassword(){
        return pass;
    }
    public String getEmail(){return email;}
    public String getEnrollment(){return enrollment;}


}
