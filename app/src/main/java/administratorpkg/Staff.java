package administratorpkg;

public class Staff {
    public String teachername;
    private String userId,pass,email;

    public Staff(String teachername,String userid,String pass,String email) {
        this.teachername = teachername;
        this.userId = userid;
        this.pass=pass;
        this.email=email;
    }

    public String getTeacherName() {
        return teachername;
    }
    public String getTeacherID(){
        return userId;
    }
    public String getpassword(){
        return pass;
    }
    public String getEmail(){return email;}



}
