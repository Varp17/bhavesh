package administratorpkg;

public class Staff {
    public String teachername;
    private String userId;

    public Staff(String teachername,String userid) {
        this.teachername = teachername;
        this.userId = userid;
    }

    public String getTeacherName() {
        return teachername;
    }
    public String getTeacherID(){
        return userId;
    }


}
