public class Course {
    private String Cno;
    private String Cname;
    private float Credit;
    private Integer Chours;
    private String Tno;

    public Course(String c, String cname, float credit, Integer chours, String t) {
        Cno = c;
        Cname = cname;
        Credit = credit;
        Chours = chours;
        Tno = t;
    }

    public String getCno() {
        return Cno;
    }

    public String getCname() {
        return Cname;
    }

    public float getCredit() {
        return Credit;
    }

    public Integer getChours() {
        return Chours;
    }

    public String getTno() {
        return Tno;
    }

    @Override
    public String toString() {
        return  Cno + "\t" + Cname + "\t"  + Credit +"\t" + Chours + "\t" + Tno + "\n";
    }
}
