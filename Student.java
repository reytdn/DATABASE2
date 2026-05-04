public class Student {
    private String studentid;
    private String lname;
    private String fname;
    private String mi;

    public Student(String studentid, String lname, String fname, String mi) {
        this.studentid = studentid;
        this.lname = lname;
        this.fname = fname;
        this.mi = mi;
    }

    public String getStudentid() {
        return studentid;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }

    public String getMi() {
        return mi;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentid + ", Last Name: " + lname + ", First Name: " + fname + ", MI: " + mi;
    }
}

