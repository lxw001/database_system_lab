public class Student {
    private String Sno;
    private String Sname;
    private String Ssex;
    private Integer Sage;
    private String Sclass;

    public Student(String s, String sname, String ssex, Integer sage, String sclass) {
        this.Sno = s;
        Sname = sname;
        Ssex = ssex;
        Sage = sage;
        Sclass = sclass;
    }

    public String getSno() {
        return Sno;
    }

    public String getSname() {
        return Sname;
    }

    public String getSsex() {
        return Ssex;
    }

    public Integer getSage() {
        return Sage;
    }

    public String getSclass() {
        return Sclass;
    }

    @Override
    public String toString() {
        return  Sno + "\t" + Sname + "\t"  + Ssex +"\t" + Sage + "\t" + Sclass + "\n";
    }
}
