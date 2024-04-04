public class SC {
    private String Cno;
    private String Sno;
    private float Score;

    public SC(String s, String c, float score) {
        Cno = c;
        Sno = s;
        Score = score;
    }

    public String getCno() {
        return Cno;
    }

    public String getSno() {
        return Sno;
    }

    public float getScore() {
        return Score;
    }

    @Override
    public String toString() {
        return  Cno + "\t" + Sno + "\t"  + Score + "\n";
    }
}
